package com.xumpy.wallbox.module.model.result;

import com.xumpy.wallbox.module.model.result.Charger;

import java.util.List;

public class Group {
    private Integer id;
    private List<Charger> chargers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Charger> getChargers() {
        return chargers;
    }

    public void setChargers(List<Charger> chargers) {
        this.chargers = chargers;
    }
}
