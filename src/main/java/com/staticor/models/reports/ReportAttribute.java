package com.staticor.models.reports;

import com.staticor.models.metrics.Chart;
import com.staticor.models.metrics.ChartAttribute;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tbl_report_attributes")
public class ReportAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "report_attr_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "report_attr_value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "report_id", referencedColumnName = "report_id")
    private Report report;

    @ManyToOne
    @JoinColumn(name = "chart_id", referencedColumnName = "chart_id")
    private Chart chart;

    @ManyToOne
    @JoinColumn(name = "attr_id", referencedColumnName = "attr_id")
    private ChartAttribute attribute;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public ChartAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(ChartAttribute attribute) {
        this.attribute = attribute;
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
