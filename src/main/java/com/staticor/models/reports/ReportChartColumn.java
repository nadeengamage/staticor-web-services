package com.staticor.models.reports;

import javax.persistence.*;

@Entity
@Table(name = "tbl_report_chart_columns")
public class ReportChartColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "column_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "column_name")
    private String column;

    @ManyToOne
    @JoinColumn(name = "report_chart_id", referencedColumnName = "report_chart_id")
    private ReportChart reportChart;

    public ReportChartColumn() {
    }

    public ReportChartColumn(String column, ReportChart reportChart) {
        this.column = column;
        this.reportChart = reportChart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumns() {
        return column;
    }

    public void setColumns(String columns) {
        this.column = columns;
    }

    public ReportChart getReportChart() {
        return reportChart;
    }

    public void setReportChart(ReportChart reportChart) {
        this.reportChart = reportChart;
    }
}
