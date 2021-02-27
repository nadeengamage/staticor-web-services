package com.staticor.models.collections;

import com.staticor.models.DateAudit;
import com.staticor.models.connections.Connection;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_collections")
public class Collection extends DateAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "col_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "col_name", nullable = false)
    private String name;

    @Column(name = "col_description")
    private String description;

    @Column(name = "col_url")
    private String url;

    private String userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "col_con_id", referencedColumnName = "con_id",
            foreignKey = @ForeignKey(name = "fk_collection_connection_id"),
            nullable = false)
    private Connection connection;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
