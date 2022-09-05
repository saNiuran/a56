package com.niule.a56.calculator.bean;

import java.io.Serializable;
import java.util.List;

public class DivisionFbaPack extends Division implements Serializable {
    private List<BaseZone> baseZoneList;

    public List<BaseZone> getBaseZoneList() {
        return baseZoneList;
    }

    public void setBaseZoneList(List<BaseZone> baseZoneList) {
        this.baseZoneList = baseZoneList;
    }
}
