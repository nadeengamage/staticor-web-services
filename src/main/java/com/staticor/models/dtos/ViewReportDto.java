package com.staticor.models.dtos;

import java.util.List;

public class ViewReportDto {

    private Long collectionId;

    private Long reportId;

    private String name;

    private String description;

    private List<ReportChartDto> charts;

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ReportChartDto> getCharts() {
        return charts;
    }

    public void setCharts(List<ReportChartDto> charts) {
        this.charts = charts;
    }
}
