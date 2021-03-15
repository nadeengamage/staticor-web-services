package com.staticor.models.dtos;

import java.util.List;
import java.util.Map;

public class ReportChartDto {

    private String type;

    private String size;

    private List<?> labels;

    private Map<Integer, List<?>> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<?> getLabels() {
        return labels;
    }

    public void setLabels(List<?> labels) {
        this.labels = labels;
    }

    public Map<Integer, List<?>> getData() {
        return data;
    }

    public void setData(Map<Integer, List<?>> data) {
        this.data = data;
    }
}
