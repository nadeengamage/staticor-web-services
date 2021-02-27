package com.staticor.models.metrics;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tbl_chart_attributes")
public class ChartAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "attr_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "attr_key")
    private String key;

    @Column(name = "attr_validation")
    private Boolean validation;

    @ManyToOne
    @JoinColumn(name = "attr_chart_id", referencedColumnName = "chart_id")
    private Chart chart;

    @Column(name = "attr_created_by", nullable = false, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "attr_created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "attr_updated_by")
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "attr_updated_at")
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getValidation() {
        return validation;
    }

    public void setValidation(Boolean validation) {
        this.validation = validation;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
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
