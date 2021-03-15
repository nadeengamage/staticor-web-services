package com.staticor.models.reports;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.staticor.models.DateAudit;
import com.staticor.models.dtos.ReportCreateDto;
import com.staticor.models.enums.ChartSize;
import com.staticor.models.enums.conveters.ChartSizeAttributeConverter;
import com.staticor.models.metrics.Chart;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_report_charts")
public class ReportChart extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "report_chart_id", updatable = false, nullable = false)
    private Long id;

    @Convert(converter = ChartSizeAttributeConverter.class)
    @Column(name = "report_chart_size", updatable = false, nullable = false)
    private ChartSize size;

    @Column(name = "report_chart_query", updatable = false, nullable = false)
    private String query;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "report_id", referencedColumnName = "report_id", foreignKey = @ForeignKey(name = "fk_report_chart_1"))
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "chart_id", referencedColumnName = "chart_id", foreignKey = @ForeignKey(name = "fk_report_columns"))
    private Chart chart;

    @OneToMany(mappedBy = "reportChart", fetch = FetchType.LAZY)
    private Set<ReportChartColumn> columns;

    public ReportChart() {
    }

    public ReportChart(ReportCreateDto createDto, Report report, Chart chart) {
        this.size = ChartSize.valueOf(createDto.getChartSize().toUpperCase());
        this.query = createDto.getSql();
        this.report = report;
        this.chart = chart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChartSize getSize() {
        return size;
    }

    public void setSize(ChartSize size) {
        this.size = size;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Set<ReportChartColumn> getColumns() {
        return columns;
    }

    public void setColumns(Set<ReportChartColumn> columns) {
        this.columns = columns;
    }
}
