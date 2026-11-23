package com.bijlipay.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class RentalSubvention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    private String ReqSetup_fees;
    private double ReqRecurringFee;
    private String appSetup_fees;
    private double appRecurringFee;

    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReqSetup_fees() {
        return ReqSetup_fees;
    }

    public void setReqSetup_fees(String reqSetup_fees) {
        ReqSetup_fees = reqSetup_fees;
    }

    public double getReqRecurringFee() {
        return ReqRecurringFee;
    }

    public void setReqRecurringFee(double reqRecurringFee) {
        ReqRecurringFee = reqRecurringFee;
    }

    public String getAppSetup_fees() {
        return appSetup_fees;
    }

    public void setAppSetup_fees(String appSetup_fees) {
        this.appSetup_fees = appSetup_fees;
    }

    public double getAppRecurringFee() {
        return appRecurringFee;
    }

    public void setAppRecurringFee(double appRecurringFee) {
        this.appRecurringFee = appRecurringFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
