package com.bijlipay.model.marsdto;

public class CustomIncentiveRatesDto {

//    private String add;
    private int maxValue;
    private Double mechantFixed;
    private int merchantPercent;
    private int minValue;
    private int sharingFixed;
    private int sharingPercent;

//    public String getAdd() {
//        return add;
//    }
//
//    public void setAdd(String add) {
//        this.add = add;
//    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public Double getMechantFixed() {
        return mechantFixed;
    }

    public void setMechantFixed(Double mechantFixed) {
        this.mechantFixed = mechantFixed;
    }

    public int getMerchantPercent() {
        return merchantPercent;
    }

    public void setMerchantPercent(int merchantPercent) {
        this.merchantPercent = merchantPercent;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getSharingFixed() {
        return sharingFixed;
    }

    public void setSharingFixed(int sharingFixed) {
        this.sharingFixed = sharingFixed;
    }

    public int getSharingPercent() {
        return sharingPercent;
    }

    public void setSharingPercent(int sharingPercent) {
        this.sharingPercent = sharingPercent;
    }
}
