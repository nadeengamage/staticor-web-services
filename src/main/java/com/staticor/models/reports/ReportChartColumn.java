package com.staticor.models.reports;

import javax.persistence.*;

@Entity
@Table(name = "tbl_report_chart_columns")
public class ReportChartColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "columns_id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chart_columns_id", referencedColumnName = "report_chart_id")
    private ReportChart reportChart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportChart getReportChart() {
        return reportChart;
    }

    public void setReportChart(ReportChart reportChart) {
        this.reportChart = reportChart;
    }
}
