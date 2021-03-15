package com.staticor.models.metrics;

import com.staticor.models.DateAudit;

import javax.persistence.*;

@Entity
@Table(name = "tbl_chart_attributes", uniqueConstraints = @UniqueConstraint(columnNames = {"attr_chart_id", "attr_key"}))
public class ChartAttribute extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "attr_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "attr_key")
    private String key;

    @Column(name = "attr_validation")
    private Boolean validation;

    @ManyToOne
    @JoinColumn(name = "attr_chart_id", referencedColumnName = "chart_id", foreignKey = @ForeignKey(name = "fk_chart_attribute"))
    private Chart chart;

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
}
