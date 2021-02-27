package com.staticor.models.dtos;

import java.util.List;
import java.util.Map;

public class ReportEditorDto {

    private Long collectionId;

    private String sql;

    private Column columns = new Column();

    private Row rows = new Row();

    private String error;

    public ReportEditorDto() {
    }

    public ReportEditorDto(String error) {
        this.error = error;
    }

    public ReportEditorDto(List<String> columns, List<Map<String, Object>> rows) {
        getColumns().setLabels(columns);
        getRows().setRecords(rows);
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Column getColumns() {
        return columns;
    }

    public void setColumns(Column columns) {
        this.columns = columns;
    }

    public Row getRows() {
        return rows;
    }

    public void setRows(Row rows) {
        this.rows = rows;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

class Column {
    private List<String> labels;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}

class Row {
    private List<Map<String, Object>> records;

    public List<Map<String, Object>> getRecords() {
        return records;
    }

    public void setRecords(List<Map<String, Object>> records) {
        this.records = records;
    }
}
