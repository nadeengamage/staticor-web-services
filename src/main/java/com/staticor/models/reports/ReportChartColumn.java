package com.staticor.models.reports;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.staticor.models.DateAudit;
import com.staticor.models.dtos.ColumnCreateDto;

import javax.persistence.*;

@Entity
@Table(name = "tbl_report_chart_columns")
public class ReportChartColumn extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "column_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "column_name")
    private String column;

    @Column(name = "column_axis")
    private String columnAxis;

    private String aliasName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "report_chart_id", referencedColumnName = "report_chart_id", foreignKey = @ForeignKey(name = "fk_report_chart_2"))
    private ReportChart reportChart;

    public ReportChartColumn() {
    }

    public ReportChartColumn(ColumnCreateDto columnCreateDto, ReportChart reportChart) {
        this.column = columnCreateDto.getName();
        this.columnAxis = columnCreateDto.getAxis();
        this.aliasName = columnCreateDto.getAliasName();
        this.reportChart = reportChart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getColumnAxis() {
        return columnAxis;
    }

    public void setColumnAxis(String columnAxis) {
        this.columnAxis = columnAxis;
    }

    public ReportChart getReportChart() {
        return reportChart;
    }

    public void setReportChart(ReportChart reportChart) {
        this.reportChart = reportChart;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }
}
