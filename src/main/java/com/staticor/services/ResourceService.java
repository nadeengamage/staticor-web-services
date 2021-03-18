package com.staticor.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.staticor.exceptions.ReportNotFoundException;
import com.staticor.models.dtos.ReportChartDto;
import com.staticor.models.dtos.ViewReportDto;
import com.staticor.models.reports.Report;
import com.staticor.models.reports.ReportChart;
import com.staticor.repositories.ReportRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.*;

@Service
public class ResourceService extends ServiceResponse {

    private final static Logger LOGGER = LogManager.getLogger(ResourceService.class);

    private final ReportRepository reportRepository;

    private final ReportService reportService;

    public ResourceService(ReportRepository reportRepository, ReportService reportService) {
        this.reportRepository = reportRepository;
        this.reportService = reportService;
    }

    // get pdf file
    public ServiceResponse generatePdfFile(ViewReportDto dto) {
        try {

            Optional<Report> report = reportRepository.findById(dto.getReportId());
            report.orElseThrow(ReportNotFoundException::new);

            Report data = report.get();

            // set charts
            List<ReportChartDto> charts = new ArrayList<>();

            Set<ReportChart> reportCharts = data.getCharts();

            for (ReportChart c : reportCharts) {
                ReportChartDto chartDto = new ReportChartDto();
                chartDto.setType(c.getChart().getName());
                chartDto.setSize(c.getSize().name());

                // get data
                chartDto.setLabels(reportService.getLabels(c.getQuery(), c, data.getCollection().getId()));
                chartDto.setData(reportService.getDataSet(c.getQuery(), c, data.getCollection().getId()));
                charts.add(chartDto);
            }

            List<List<String>> lists = wrapperData(charts);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            // set report name
            PdfPTable pageHeader = new PdfPTable(1);
            PdfPCell pageHeaderCell = new PdfPCell(new Phrase(data.getName().toUpperCase()));
            pageHeaderCell.setBorder(0);
            pageHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pageHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pageHeader.addCell(pageHeaderCell);

            document.add(pageHeader);

            if (lists.size() > 0) {
                PdfPTable table = new PdfPTable(lists.get(0).size());
                table.setSpacingBefore(30f);
                lists.forEach(values -> {
                    for (int i = 0; i < values.size(); i++) {
                        if (i == 0 && values.get(i) != null) {
                            PdfPCell header = new PdfPCell();
                            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            header.setHorizontalAlignment(Element.ALIGN_CENTER);
                            header.setBorderWidth(2);
                            header.setPhrase(new Phrase(values.get(i), headFont));
                            table.addCell(header);
                        }

                        if (i == 1 && values.get(i) != null) {
                            PdfPCell cell = new PdfPCell(new Phrase(values.get(i)));
                            cell.setPaddingLeft(4);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);
                        }

                        if (i == 2 && values.get(i) != null) {
                            PdfPCell cell = new PdfPCell(new Phrase(values.get(i)));
                            cell.setPaddingLeft(4);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);
                        }
                    }
                });
                document.add(table);
            }
            document.close();

            return success(true).code(200).message("Data Saved!").result(new ByteArrayInputStream(out.toByteArray()));
        } catch (Exception e) {
            LOGGER.error("SQL editor error " + e.getMessage());
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }

    private List<List<String>> wrapperData(List<ReportChartDto> charts) {
        List<List<String>> response = new ArrayList<>();
        List<?> labels = charts.get(0).getLabels();
        List<?> axis1 = charts.get(0).getData().get(0);
        List<?> axis2 = null;
        if (charts.get(0).getData().size() == 2) {
            axis2 = charts.get(0).getData().get(1);
        }

        for (int i = 0; i < labels.size(); i++) {
            HashMap<String, Object> label = (HashMap<String, Object>) labels.get(i);
            HashMap<String, Object> axis1Data = (HashMap<String, Object>) axis1.get(i);
            HashMap<String, Object> axis2Data = (axis2 != null ? (HashMap<String, Object>) axis2.get(i) : null);

            response.add(i, Arrays.asList(label.entrySet().iterator().next().getValue().toString(),
                    axis1Data.entrySet().iterator().next().getValue().toString(),
                    (axis2Data != null ? axis2Data.entrySet().iterator().next().getValue().toString() : null)));
        }

        return response;
    }
}
