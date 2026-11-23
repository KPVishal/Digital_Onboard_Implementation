package com.bijlipay.model.dto;

public class ImplementationUpdate {
    private String leadNumber;
    private int status;

    private String tid;


    public String getLeadNumber() {
        return leadNumber;
    }

    public void setLeadNumber(String leadNumber) {
        this.leadNumber = leadNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
