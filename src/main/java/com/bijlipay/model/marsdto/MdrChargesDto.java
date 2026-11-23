package com.bijlipay.model.marsdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MdrChargesDto {

    @JsonProperty
    private double fixed;
    @JsonProperty
    private double minimum;
    @JsonProperty
    private double percentage;

//    private int fixed;
//    private int minimum;
//    private int percentage;
    private String minimumTxnValue;

//    public int getFixed() {
//        return fixed;
//    }
//
//    public void setFixed(int fixed) {
//        this.fixed = fixed;
//    }
//
//    public int getMinimum() {
//        return minimum;
//    }
//
//    public void setMinimum(int minimum) {
//        this.minimum = minimum;
//    }
//
//    public int getPercentage() {
//        return percentage;
//    }

//    public void setPercentage(int percentage) {
//        this.percentage = percentage;
//    }

    public double getFixed() {
        return fixed;
    }

    public void setFixed(double fixed) {
        this.fixed = fixed;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getMinimumTxnValue() {
        return minimumTxnValue;
    }

    public void setMinimumTxnValue(String minimumTxnValue) {
        this.minimumTxnValue = minimumTxnValue;
    }
}
