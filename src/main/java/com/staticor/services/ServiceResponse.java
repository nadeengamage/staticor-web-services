package com.staticor.services;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public abstract class ServiceResponse {

    @JsonIgnore
    private Integer code;

    private String message;

    private Object result;

    private Object resultInfo;

    private Boolean success;

    private Object errors;

    public Integer getCode() {
        return code;
    }

    public ServiceResponse code(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ServiceResponse message(String message) {
        this.message = message;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public ServiceResponse result(Object result) {
        this.result = result;
        return this;
    }

    public Object getResultInfo() {
        return resultInfo;
    }

    public ServiceResponse resultInfo(Object resultInfo) {
        this.resultInfo = resultInfo;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public ServiceResponse success(Boolean success) {
        this.code = 0;
        this.message = null;
        this.result = new ArrayList<>();
        this.resultInfo = new ArrayList<>();
        this.success = success;
        this.errors = new ArrayList<>();
        return this;
    }

    public Object getErrors() {
        return errors;
    }

    public ServiceResponse errors(Object errors) {
        this.errors = errors;
        return this;
    }
}
