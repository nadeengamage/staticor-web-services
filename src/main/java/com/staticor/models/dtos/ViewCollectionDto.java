package com.staticor.models.dtos;

import java.util.Objects;

public class ViewCollectionDto {

    private Long collectionId;

    private String name;

    private String description;

    private Object connection;

    private Object database;

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
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

    public Object getConnection() {
        return connection;
    }

    public void setConnection(Object connection) {
        this.connection = connection;
    }

    public Object getDatabase() {
        return database;
    }

    public void setDatabase(Object database) {
        this.database = database;
    }
}
