package com.niule.a56.calculator.bean;

public class PriceRow {
    private long id;

    private long freightLineId;

    private long cargoId;

    private long divisionId;

    private long chargeUnitId;

    private int volumeRate;

    private String memo;

    private String timeMemo;

    private int withSmallPackage;

    private int smallPackageFirstWeightPrice;

    private int smallPackageWeightStart;

    private int smallPackageWeightRange;

    private int smallPackageWeightEnd;

    private int smallPackageUnitPrice;

    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFreightLineId() {
        return freightLineId;
    }

    public void setFreightLineId(long freightLineId) {
        this.freightLineId = freightLineId;
    }

    public long getCargoId() {
        return cargoId;
    }

    public void setCargoId(long cargoId) {
        this.cargoId = cargoId;
    }

    public long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    public long getChargeUnitId() {
        return chargeUnitId;
    }

    public void setChargeUnitId(long chargeUnitId) {
        this.chargeUnitId = chargeUnitId;
    }

    public int getVolumeRate() {
        return volumeRate;
    }

    public void setVolumeRate(int volumeRate) {
        this.volumeRate = volumeRate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getTimeMemo() {
        return timeMemo;
    }

    public void setTimeMemo(String timeMemo) {
        this.timeMemo = timeMemo == null ? null : timeMemo.trim();
    }

    public int getWithSmallPackage() {
        return withSmallPackage;
    }

    public void setWithSmallPackage(int withSmallPackage) {
        this.withSmallPackage = withSmallPackage;
    }

    public int getSmallPackageFirstWeightPrice() {
        return smallPackageFirstWeightPrice;
    }

    public void setSmallPackageFirstWeightPrice(int smallPackageFirstWeightPrice) {
        this.smallPackageFirstWeightPrice = smallPackageFirstWeightPrice;
    }

    public int getSmallPackageWeightStart() {
        return smallPackageWeightStart;
    }

    public void setSmallPackageWeightStart(int smallPackageWeightStart) {
        this.smallPackageWeightStart = smallPackageWeightStart;
    }

    public int getSmallPackageWeightRange() {
        return smallPackageWeightRange;
    }

    public void setSmallPackageWeightRange(int smallPackageWeightRange) {
        this.smallPackageWeightRange = smallPackageWeightRange;
    }

    public int getSmallPackageWeightEnd() {
        return smallPackageWeightEnd;
    }

    public void setSmallPackageWeightEnd(int smallPackageWeightEnd) {
        this.smallPackageWeightEnd = smallPackageWeightEnd;
    }

    public int getSmallPackageUnitPrice() {
        return smallPackageUnitPrice;
    }

    public void setSmallPackageUnitPrice(int smallPackageUnitPrice) {
        this.smallPackageUnitPrice = smallPackageUnitPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}