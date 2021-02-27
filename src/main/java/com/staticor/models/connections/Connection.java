package com.staticor.models.connections;

import com.staticor.models.DateAudit;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_connections")
public class Connection extends DateAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "con_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "con_name", nullable = false)
    private String name;

    @Column(name = "con_description", nullable = false)
    private String description;

    @Column(name = "con_host", nullable = false)
    private String host;

    @Column(name = "con_port", nullable = false)
    private String port;

    @Column(name = "con_db_name", nullable = false)
    private String databaseName;

    @Column(name = "con_username", nullable = false)
    private String username;

    @Column(name = "con_password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "con_db_id", referencedColumnName = "db_id", nullable = false)
    private Database database;

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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
