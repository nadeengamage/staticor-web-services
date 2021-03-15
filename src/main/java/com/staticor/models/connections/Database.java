package com.staticor.models.connections;

import com.staticor.models.DateAudit;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_databases")
public class Database extends DateAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "db_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "db_name", updatable = false, nullable = false, unique = true)
    private String name;

    @Column(name = "db_driver", updatable = false, nullable = false, unique = true)
    private String driver;

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

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
