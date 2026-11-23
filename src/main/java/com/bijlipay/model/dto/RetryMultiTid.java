package com.bijlipay.model.dto;

public class RetryMultiTid {
    private String applicationNumber;
    private int insCode;


    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }


    public int getInsCode() {
        return insCode;
    }

    public void setInsCode(int insCode) {
        this.insCode = insCode;
    }
}
