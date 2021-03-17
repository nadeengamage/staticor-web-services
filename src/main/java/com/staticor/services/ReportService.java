package com.staticor.services;

import com.staticor.exceptions.ChartNotFoundException;
import com.staticor.exceptions.CollectionNotFoundException;
import com.staticor.exceptions.ReportNotFoundException;
import com.staticor.models.collections.Collection;
import com.staticor.models.connections.Connection;
import com.staticor.models.dtos.*;
import com.staticor.models.enums.ChartSize;
import com.staticor.models.metrics.Chart;
import com.staticor.models.reports.Report;
import com.staticor.models.reports.ReportChart;
import com.staticor.models.reports.ReportChartColumn;
import com.staticor.repositories.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReportService extends ServiceResponse {

    private final static Logger LOGGER = LogManager.getLogger(ReportService.class);

    private final String[] words = {"ALTER", "TRUNCATE", "COMMENT", "RENAME", "INSERT", "UPDATE", "DELETE", "GRANT", "REVOKE", "COMMIT", "ROLLBACK", "SAVEPOINT", "SET", "TRANSACTION"};

    private final ReportRepository repository;

    private final CollectionRepository collectionRepository;

    private final ConnectionService connectionService;

    private final ChartRepository chartRepository;

    private final ReportChartRepository reportChartRepository;

    private final ReportChartColumnRepository reportChartColumnRepository;

    public ReportService(ReportRepository repository,
                         CollectionRepository collectionRepository,
                         ConnectionService connectionService,
                         ChartRepository chartRepository,
                         ReportChartRepository reportChartRepository,
                         ReportChartColumnRepository reportChartColumnRepository) {
        this.repository = repository;
        this.collectionRepository = collectionRepository;
        this.connectionService = connectionService;
        this.chartRepository = chartRepository;
        this.reportChartRepository = reportChartRepository;
        this.reportChartColumnRepository = reportChartColumnRepository;
    }

    public ServiceResponse executeSqlQuery(ReportEditorDto editor) {

        List<String> columns = new ArrayList<>();
        List<Map<String, Object>> rows = new ArrayList<>();

        Optional<Collection> collection = collectionRepository.findById(editor.getCollectionId());
        collection.orElseThrow(RuntimeException::new);

        try {

            // check sql query
            if (containsWords(editor.getSql().toUpperCase())) {
                return success(false).code(400).message("You haven't authorization to execute this given SQL query");
            }

            Connection connection = collection.get().getConnection();

            String url = String.format("jdbc:mysql://%s:%s/%s?createIfNotExists=true&useSSL=false", connection.getHost(), connection.getPort(), connection.getDatabaseName());

            java.sql.Connection con = connectionService.getConnection(url, connection);
            PreparedStatement ps = con.prepareStatement(editor.getSql());
            ResultSet rs = ps.executeQuery();

            // get columns
            getColumns(rs, columns);

            // get values
            getRows(rs, columns, rows);

            rs.close();
            ps.close();
            con.close();

            return success(true).code(200).result(new ReportEditorDto(columns, rows));
        } catch (SQLException e) {
            LOGGER.error("SQL editor error " + e.getSQLState());
            return success(false).code(500).message("Invalid SQL - " + e.getSQLState()).errors(e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.error("SQL editor error " + e.getMessage());
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }

    private void getColumns(ResultSet rs, List<String> columns) throws SQLException {
        if (rs != null) {
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                columns.add(rsmd.getColumnName(i));
            }
        }
    }

    private void getRows(ResultSet rs, List<String> columns, List<Map<String, Object>> rows) throws SQLException {
        if (rs != null) {
            if (rs.isBeforeFirst()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columns.size(); i++) {
                        String colName = rsmd.getColumnName(i);
                        Object colVal = rs.getObject(i);
                        row.put(colName, colVal);
                    }
                    rows.add(row);
                }
            }
        }
    }

    public boolean containsWords(String inputString) {
        boolean found = false;
        for (String word : words) {
            if (inputString.contains(word)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public ServiceResponse createNewReport(ReportCreateDto createDto) {
        try {

            Optional<Collection> collection = collectionRepository.findById(createDto.getCollectionId());
            collection.orElseThrow(CollectionNotFoundException::new);

            // create new report
            Report report = new Report(createDto, collection.get());
            repository.save(report);
            createDto.setReportId(report.getId());

            // create report chart
            Optional<Chart> chart = chartRepository.findById(createDto.getChartId());
            chart.orElseThrow(ChartNotFoundException::new);

            ReportChart reportChart = new ReportChart(createDto, report, chart.get());
            reportChartRepository.save(reportChart);

            // creat report chart columns
            createDto.getColumns().forEach(col -> {
                ReportChartColumn column = new ReportChartColumn(col, reportChart);
                reportChartColumnRepository.save(column);
            });

            return success(true).code(200).message("Data Saved!").result(createDto);
        } catch (Exception e) {
            LOGGER.error("SQL editor error " + e.getMessage());
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }

    public ServiceResponse viewReport(ViewReportDto reportDto) {
        try {

            Optional<Report> report = repository.findById(reportDto.getReportId());
            report.orElseThrow(ReportNotFoundException::new);

            Report data = report.get();

            // set charts
            List<ReportChartDto> charts = new ArrayList<>();

            Set<ReportChart> reportCharts = data.getCharts();

            List<String> alias = new ArrayList<>();

            for (ReportChart c : reportCharts) {
                ReportChartDto chartDto = new ReportChartDto();
                chartDto.setType(c.getChart().getName());
                chartDto.setSize(c.getSize().name());

                // get data
                chartDto.setLabels(getLabels(c.getQuery(), c, data.getCollection().getId()));
                chartDto.setData(getDataSet(c.getQuery(), c, data.getCollection().getId()));

                c.getColumns().forEach(al -> {
                    if (al.getAliasName() != null && al.getColumnAxis().equalsIgnoreCase("Y_1")) {
                        alias.add(0, al.getAliasName());
                    }

                    if (al.getAliasName() != null && al.getColumnAxis().equalsIgnoreCase("Y_2")) {
                        alias.add(1, al.getAliasName());
                    }
                });

                chartDto.setAlias(alias);
                charts.add(chartDto);
            }

            ViewReportDto view = new ViewReportDto();
            view.setReportId(data.getId());
            view.setName(data.getName());
            view.setDescription(data.getDescriptions());
            view.setCharts(charts);

            return success(true).code(200).message("Data Saved!").result(view);
        } catch (Exception e) {
            LOGGER.error("SQL editor error " + e.getMessage());
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }

    public List<?> getLabels(String sql, ReportChart reportChart, Long collectionId) throws ChartNotFoundException, SQLException {
        String[] from = sql.toLowerCase().split("from");

        Optional<ReportChartColumn> reportChartColumn = reportChartColumnRepository.getByReportChartAndColumnAxis(reportChart, "X_1");
        reportChartColumn.orElseThrow(ChartNotFoundException::new);

        String newSql = String.format("select %s from %s", reportChartColumn.get().getColumn(), from[1]);

        return getData(collectionId, newSql).get("rows");
    }

    public Map<Integer, List<?>> getDataSet(String sql, ReportChart reportChart, Long collectionId) throws ChartNotFoundException, SQLException {
        String[] from = sql.toLowerCase().split("from");
        Map<Integer, List<?>> data = new HashMap<>();

        List<String> columns = reportChart.getColumns().stream()
                .filter(col -> col.getColumnAxis().contains("Y_"))
                .map(ReportChartColumn::getColumn)
                .collect(Collectors.toList());

        Stream<String> queries = columns.stream().map(col -> "select " + col.toLowerCase() + " from " + from[1]);
        AtomicInteger idx = new AtomicInteger();
        queries.forEach(q -> {
            try {
                data.put(idx.getAndIncrement(), getData(collectionId, q).get("rows"));
            } catch (SQLException throwable) {
                throw new IllegalArgumentException(throwable);
            }
        });

        return data;
    }

    private Map<String, List<?>> getData(Long collectionId, String sql) throws SQLException {
        List<String> columns = new ArrayList<>();
        List<Map<String, Object>> rows = new ArrayList<>();

        Optional<Collection> collection = collectionRepository.findById(collectionId);
        collection.orElseThrow(RuntimeException::new);

        Connection connection = collection.get().getConnection();

        String url = String.format("jdbc:mysql://%s:%s/%s?createIfNotExists=true&useSSL=false", connection.getHost(), connection.getPort(), connection.getDatabaseName());

        java.sql.Connection con = connectionService.getConnection(url, connection);
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // get columns
        getColumns(rs, columns);

        // get values
        getRows(rs, columns, rows);

        rs.close();
        ps.close();
        con.close();

        return Map.of("rows", rows);
    }

    public ServiceResponse getReportById(Long id) {
        try {
            Optional<Report> report = repository.findById(id);
            report.orElseThrow(ReportNotFoundException::new);

            ReportEditorDto editorDto = new ReportEditorDto();
            editorDto.setCollectionId(report.get().getCollection().getId());
            editorDto.setSql(report.get().getCharts().iterator().next().getQuery());

            ServiceResponse serviceResponse = executeSqlQuery(editorDto);
            Object result = serviceResponse.getResult();

            GetReportDto getReportDto = new GetReportDto();
            getReportDto.setReport(report);
            getReportDto.setEditor(result);

            return success(true).code(200).result(getReportDto);
        } catch (Exception e) {
            LOGGER.error("SQL editor error " + e.getMessage());
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }

    public ServiceResponse updateReport(ReportCreateDto createDto) {
        try {
            // create new report
            Optional<Report> report = repository.findById(createDto.getReportId());
            report.orElseThrow(ReportNotFoundException::new);

            Report reportData = report.get();
            reportData.setName(createDto.getReportName());
            reportData.setDescriptions(createDto.getReportDesc());
            repository.save(reportData);

            createDto.setReportId(reportData.getId());

            // create report chart
            Optional<Chart> chart = chartRepository.findById(createDto.getChartId());
            chart.orElseThrow(ChartNotFoundException::new);

            ReportChart reportChart = reportChartRepository.findByReport(reportData);
            reportChart.setChart(chart.get());
            reportChart.setQuery(createDto.getSql());
            reportChart.setSize(ChartSize.valueOf(createDto.getChartSize().toUpperCase()));
            reportChartRepository.save(reportChart);

            // creat report chart columns
            for (ColumnCreateDto col : createDto.getColumns()) {
                Optional<ReportChartColumn> column = reportChartColumnRepository.getByReportChartAndColumnAxis(reportChart, col.getAxis());
                column.orElseThrow(ChartNotFoundException::new);

                ReportChartColumn columnData = column.get();
                columnData.setColumn(col.getName());
                columnData.setColumnAxis(col.getAxis());
                columnData.setAliasName(col.getAliasName());
                reportChartColumnRepository.save(columnData);
            }

            return success(true).code(200).message("Data Saved!").result(createDto);
        } catch (Exception e) {
            LOGGER.error("SQL editor error " + e.getMessage());
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }

    public ServiceResponse deleteReport(Long id) {
        try {
            Optional<Report> report = repository.findById(id);
            report.orElseThrow(ReportNotFoundException::new);

            Report reportData = report.get();

            repository.delete(reportData);

            return success(true).code(200).message("Data deleted!");
        } catch (Exception e) {
            LOGGER.error("SQL editor error " + e.getMessage());
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }
}