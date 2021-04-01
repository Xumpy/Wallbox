package com.xumpy.wallbox.module.model;

public class Status {
    private Integer status_id;
    private String status_description;
    private String name;

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public String getStatus_description() {
        return status_description;
    }

    public void setStatus_description(String status_description) {
        this.status_description = status_description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
