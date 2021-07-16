package com.xumpy.wallbox.module.model;

import java.math.BigDecimal;

public class Authentication {
    private String jwt;
    private Integer user_id;
    private BigDecimal ttl;
    private Boolean error;
    private Integer status;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public BigDecimal getTtl() {
        return ttl;
    }

    public void setTtl(BigDecimal ttl) {
        this.ttl = ttl;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
