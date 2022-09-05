package com.niule.a56.calculator.bean;

import com.github.gzuliyujiang.wheelview.contract.TextProvider;

public class FreightLine implements TextProvider {
    private long id;

    private String name;

    private long baseFreightId;

    private long destinationCountryId;

    private String departurePort;

    private long supplierId;

    private Integer open;

    private String sort;

    private long pointIdSameMemo;

    private long cargoId;

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

    public long getBaseFreightId() {
        return baseFreightId;
    }

    public void setBaseFreightId(long baseFreightId) {
        this.baseFreightId = baseFreightId;
    }

    public long getDestinationCountryId() {
        return destinationCountryId;
    }

    public void setDestinationCountryId(long destinationCountryId) {
        this.destinationCountryId = destinationCountryId;
    }

    public String getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(String departurePort) {
        this.departurePort = departurePort;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public long getPointIdSameMemo() {
        return pointIdSameMemo;
    }

    public void setPointIdSameMemo(long pointIdSameMemo) {
        this.pointIdSameMemo = pointIdSameMemo;
    }

    public long getCargoId() {
        return cargoId;
    }

    public void setCargoId(long cargoId) {
        this.cargoId = cargoId;
    }

    @Override
    public String provideText() {
        return getName();
    }
}