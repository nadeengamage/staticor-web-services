package com.staticor.models.metrics;

import com.staticor.models.DateAudit;

import javax.persistence.*;

@Entity
@Table(name = "tbl_charts")
public class Chart extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "chart_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "chart_name", nullable = false, unique = true)
    private String name;

    @Column(name = "chart_desc", nullable = false)
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
