package com.niule.a56.calculator.bean;


import java.io.Serializable;

public class CountryResponse extends Country implements Serializable {
    private String continentName;

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }
}
