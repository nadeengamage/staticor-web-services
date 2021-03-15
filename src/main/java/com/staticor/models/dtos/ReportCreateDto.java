package com.staticor.models.dtos;

import java.util.Set;

public class ReportCreateDto {

    private Long reportId;

    private Long collectionId;

    private String reportName;

    private String reportDesc;

    private Long chartId;

    private String chartSize;

    private String sql;

    private Set<ColumnCreateDto> columns;

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportDesc() {
        return reportDesc;
    }

    public void setReportDesc(String reportDesc) {
        this.reportDesc = reportDesc;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public String getChartSize() {
        return chartSize;
    }

    public void setChartSize(String chartSize) {
        this.chartSize = chartSize;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Set<ColumnCreateDto> getColumns() {
        return columns;
    }

    public void setColumns(Set<ColumnCreateDto> columns) {
        this.columns = columns;
    }
}

