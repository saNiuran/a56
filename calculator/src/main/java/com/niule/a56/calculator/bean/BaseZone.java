package com.niule.a56.calculator.bean;

import com.github.gzuliyujiang.wheelview.contract.TextProvider;

public class BaseZone implements TextProvider {
    private long id;

    private String name;

    private int type;

    private long countryId;

    private String fbaAddress;

    private String fbaCity;

    private String fbaState;

    private String fbaZip;

    private String fbaDischargePort;

    private String fbaPickupCity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getFbaAddress() {
        return fbaAddress;
    }

    public void setFbaAddress(String fbaAddress) {
        this.fbaAddress = fbaAddress;
    }

    public String getFbaCity() {
        return fbaCity;
    }

    public void setFbaCity(String fbaCity) {
        this.fbaCity = fbaCity;
    }

    public String getFbaState() {
        return fbaState;
    }

    public void setFbaState(String fbaState) {
        this.fbaState = fbaState;
    }

    public String getFbaZip() {
        return fbaZip;
    }

    public void setFbaZip(String fbaZip) {
        this.fbaZip = fbaZip;
    }

    public String getFbaDischargePort() {
        return fbaDischargePort;
    }

    public void setFbaDischargePort(String fbaDischargePort) {
        this.fbaDischargePort = fbaDischargePort;
    }

    public String getFbaPickupCity() {
        return fbaPickupCity;
    }

    public void setFbaPickupCity(String fbaPickupCity) {
        this.fbaPickupCity = fbaPickupCity;
    }

    @Override
    public String provideText() {
        return getName();
    }
}