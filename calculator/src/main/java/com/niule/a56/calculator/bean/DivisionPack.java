package com.niule.a56.calculator.bean;

import java.io.Serializable;
import java.util.List;

public class DivisionPack implements Serializable {
    private FreightLine freightLine;
    private int hasDivision;
    private List<Division> divisionList;
    private List<DivisionFbaPack> fbaPackList;

    public FreightLine getFreightLine() {
        return freightLine;
    }

    public void setFreightLine(FreightLine freightLine) {
        this.freightLine = freightLine;
    }

    public int getHasDivision() {
        return hasDivision;
    }

    public void setHasDivision(int hasDivision) {
        this.hasDivision = hasDivision;
    }

    public List<Division> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(List<Division> divisionList) {
        this.divisionList = divisionList;
    }

    public List<DivisionFbaPack> getFbaPackList() {
        return fbaPackList;
    }

    public void setFbaPackList(List<DivisionFbaPack> fbaPackList) {
        this.fbaPackList = fbaPackList;
    }
}
