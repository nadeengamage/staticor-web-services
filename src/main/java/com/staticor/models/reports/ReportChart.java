package com.staticor.models.reports;

import com.staticor.models.enums.ChartSize;
import com.staticor.models.enums.conveters.ChartSizeAttributeConverter;
import com.staticor.models.metrics.Chart;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_report_charts")
public class ReportChart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "report_chart_id", updatable = false, nullable = false)
    private Long id;

    @Convert(converter = ChartSizeAttributeConverter.class)
    @Column(name = "report_chart_size", updatable = false, nullable = false)
    private ChartSize size;

    @Column(name = "report_chart_query", updatable = false, nullable = false)
    private String query;

    @ManyToOne
    @JoinColumn(name = "report_id", referencedColumnName = "report_id")
    private Report report;

    @ManyToOne
    @JoinColumn(name = "chart_id", referencedColumnName = "chart_id")
    private Chart chart;

    @OneToMany(mappedBy = "reportChart")
    private Set<ReportChartColumn> columns;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
