package com.xumpy.wallbox.module.model.result;

public class Charger {
    private Integer id;
    private Integer status;
    private String statusDescription;
    private String chargerType;
    private Integer locked;
    private Integer maxChargingCurrent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getChargerType() {
        return chargerType;
    }

    public void setChargerType(String chargerType) {
        this.chargerType = chargerType;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Integer getMaxChargingCurrent() {
        return maxChargingCurrent;
    }

    public void setMaxChargingCurrent(Integer maxChargingCurrent) {
        this.maxChargingCurrent = maxChargingCurrent;
    }
}
