package com.niule.a56.calculator.bean;


import java.io.Serializable;
import java.util.List;

public class FreightLineResponse extends FreightLine implements Serializable {
    private CountryResponse country;
    private BaseFreight baseFreight;
    private Supplier supplier;
    private List<Division> divisionList;

    public CountryResponse getCountry() {
        return country;
    }

    public void setCountry(CountryResponse country) {
        this.country = country;
    }

    public BaseFreight getBaseFreight() {
        return baseFreight;
    }

    public void setBaseFreight(BaseFreight baseFreight) {
        this.baseFreight = baseFreight;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Division> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(List<Division> divisionList) {
        this.divisionList = divisionList;
    }
}
