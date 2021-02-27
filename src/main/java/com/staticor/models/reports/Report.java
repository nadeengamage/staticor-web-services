package com.staticor.models.reports;

import com.staticor.models.collections.Collection;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "report_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "report_name")
    private String name;

    @Column(name = "report_desc")
    private String descriptions;

    @ManyToOne
    @JoinColumn(name = "report_col_id", referencedColumnName = "col_id")
    private Collection collection;

    @OneToMany(mappedBy = "chart")
    private Set<ReportChart> charts;

    @OneToMany(mappedBy = "report")
    private Set<ReportAttribute> attributes;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Set<ReportChart> getCharts() {
        return charts;
    }

    public void setCharts(Set<ReportChart> charts) {
        this.charts = charts;
    }

    public Set<ReportAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<ReportAttribute> attributes) {
        this.attributes = attributes;
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
