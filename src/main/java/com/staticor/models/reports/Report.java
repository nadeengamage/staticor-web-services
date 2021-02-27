package com.staticor.models.reports;

import com.staticor.models.DateAudit;
import com.staticor.models.collections.Collection;
import com.staticor.models.dtos.ReportCreateDto;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_reports")
public class Report extends DateAudit {

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

    public Report() {
    }

    public Report(ReportCreateDto createDto, Collection collection) {
        this.name = createDto.getReportName().toUpperCase();
        this.descriptions = createDto.getReportDesc().toUpperCase();
        this.collection = collection;
    }

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
}
