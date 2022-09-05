package com.niule.a56.calculator.bean;


import java.io.Serializable;
import java.util.List;

public class ContinentPack extends Continent implements Serializable {
    private List<CountryResponse> countryList;

    public List<CountryResponse> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<CountryResponse> countryList) {
        this.countryList = countryList;
    }
}
