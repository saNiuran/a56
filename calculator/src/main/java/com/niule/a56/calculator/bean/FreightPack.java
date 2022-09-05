package com.niule.a56.calculator.bean;


import java.io.Serializable;
import java.util.List;

public class FreightPack extends BaseFreight implements Serializable {
    private List<FreightLineResponse> freightLineList;

    public List<FreightLineResponse> getFreightLineList() {
        return freightLineList;
    }

    public void setFreightLineList(List<FreightLineResponse> freightLineList) {
        this.freightLineList = freightLineList;
    }
}
