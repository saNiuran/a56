package com.niule.a56.calculator.bean;

import java.util.List;

public class Options {
    private long countryId;
    private String countryName;
    private long freightLineId;
    private String freightLineName;
    private long divisionId;
    private String divisionName;
    private long fbaId;
    private String fbaName;
    private boolean showFreightLine;
    private boolean showZipZone;
    private boolean showCloudZone;
    private boolean showFbaZone;

    private int totalWeight;
    private int totalCartons;
    private List<CartonInfo> cartonInfoList;
    private int current;
    private int bulkWeight;
    private int bulkVolume;

    private int cargoId;
    private int volumeRate;
    private int chargeUnitId;

    @Override
    public String toString() {
        return "Options{" +
                "countryId=" + countryId +
                ", countryName='" + countryName + '\'' +
                ", freightLineId=" + freightLineId +
                ", freightLineName='" + freightLineName + '\'' +
                ", divisionId=" + divisionId +
                ", divisionName='" + divisionName + '\'' +
                ", fbaId=" + fbaId +
                ", fbaName='" + fbaName + '\'' +
                ", showFreightLine=" + showFreightLine +
                ", showZipZone=" + showZipZone +
                ", showCloudZone=" + showCloudZone +
                ", showFbaZone=" + showFbaZone +
                ", totalWeight=" + totalWeight +
                ", totalCartons=" + totalCartons +
                ", cartonInfoList=" + (cartonInfoList==null?"null":cartonInfoList.toString()) +
                ", current=" + current +
                ", bulkWeight=" + bulkWeight +
                ", bulkVolume=" + bulkVolume +
                ", cargoId=" + cargoId +
                ", volumeRate=" + volumeRate +
                ", chargeUnitId=" + chargeUnitId +
                '}';
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public long getFreightLineId() {
        return freightLineId;
    }

    public void setFreightLineId(long freightLineId) {
        this.freightLineId = freightLineId;
    }

    public String getFreightLineName() {
        return freightLineName;
    }

    public void setFreightLineName(String freightLineName) {
        this.freightLineName = freightLineName;
    }

    public long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public long getFbaId() {
        return fbaId;
    }

    public void setFbaId(long fbaId) {
        this.fbaId = fbaId;
    }

    public String getFbaName() {
        return fbaName;
    }

    public void setFbaName(String fbaName) {
        this.fbaName = fbaName;
    }

    public boolean isShowFreightLine() {
        return showFreightLine;
    }

    public void setShowFreightLine(boolean showFreightLine) {
        this.showFreightLine = showFreightLine;
    }

    public boolean isShowZipZone() {
        return showZipZone;
    }

    public void setShowZipZone(boolean showZipZone) {
        this.showZipZone = showZipZone;
    }

    public boolean isShowCloudZone() {
        return showCloudZone;
    }

    public void setShowCloudZone(boolean showCloudZone) {
        this.showCloudZone = showCloudZone;
    }

    public boolean isShowFbaZone() {
        return showFbaZone;
    }

    public void setShowFbaZone(boolean showFbaZone) {
        this.showFbaZone = showFbaZone;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int getTotalCartons() {
        return totalCartons;
    }

    public void setTotalCartons(int totalCartons) {
        this.totalCartons = totalCartons;
    }

    public List<CartonInfo> getCartonInfoList() {
        return cartonInfoList;
    }

    public void setCartonInfoList(List<CartonInfo> cartonInfoList) {
        this.cartonInfoList = cartonInfoList;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getBulkWeight() {
        return bulkWeight;
    }

    public void setBulkWeight(int bulkWeight) {
        this.bulkWeight = bulkWeight;
    }

    public int getBulkVolume() {
        return bulkVolume;
    }

    public void setBulkVolume(int bulkVolume) {
        this.bulkVolume = bulkVolume;
    }

    public int getCargoId() {
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public int getVolumeRate() {
        return volumeRate;
    }

    public void setVolumeRate(int volumeRate) {
        this.volumeRate = volumeRate;
    }

    public int getChargeUnitId() {
        return chargeUnitId;
    }

    public void setChargeUnitId(int chargeUnitId) {
        this.chargeUnitId = chargeUnitId;
    }
}
