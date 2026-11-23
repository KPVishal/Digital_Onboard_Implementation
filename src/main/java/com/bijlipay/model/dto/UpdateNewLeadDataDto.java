package com.bijlipay.model.dto;

import com.bijlipay.model.User;

public class UpdateNewLeadDataDto {
    private String leadNumber;
    private String leadName;
    private String contactName;
    private String contactNumber;
    private String reason;
    private String soName;
    private String leadSource;
    private String merchantType;
    private String city;
    private String plan;
    private String device;
    private int  deviceCount;
    private String merchantCategory;
    private double setupFees;
    private double recurringFees;
    private double debitLessthanAmount;
    private double debitGreaterthanAmount;
    private double stdCC;
    private double premiumCC;
    private double corpCC;
    private double intlCC;
    private double superPremiumlCC;
    private String posEnable;
    private int cmsLeadStatus;

    private double setUpFeeAppliedAmount;
    private double recurringFeeAppliedAmount;
    private double appliedDebitLessthanAmount;
    private double appliedDebitGreaterthanAmount;
    private double appliedStdCC;
    private double appliedPremiumCC;
    private double appliedCorpCC;
    private double appliedIntlCC;
    private double appliedSuperPremiumCC;
    private String region;

    private String contactEmail;
    private String contactAddress;
    private User assignedToNH;
    private boolean isMdrSubventionRequested;
    private boolean isRentalSubventionRequested;
    private int leadCategory;

    public String getLeadNumber() {
        return leadNumber;
    }

    public void setLeadNumber(String leadNumber) {
        this.leadNumber = leadNumber;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSoName() {
        return soName;
    }

    public void setSoName(String soName) {
        this.soName = soName;
    }

    public String getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(String leadSource) {
        this.leadSource = leadSource;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

    public String getMerchantCategory() {
        return merchantCategory;
    }

    public void setMerchantCategory(String merchantCategory) {
        this.merchantCategory = merchantCategory;
    }

    public double getSetupFees() {
        return setupFees;
    }

    public void setSetupFees(double setupFees) {
        this.setupFees = setupFees;
    }

    public double getRecurringFees() {
        return recurringFees;
    }

    public void setRecurringFees(double recurringFees) {
        this.recurringFees = recurringFees;
    }

    public double getDebitLessthanAmount() {
        return debitLessthanAmount;
    }

    public void setDebitLessthanAmount(double debitLessthanAmount) {
        this.debitLessthanAmount = debitLessthanAmount;
    }

    public double getDebitGreaterthanAmount() {
        return debitGreaterthanAmount;
    }

    public void setDebitGreaterthanAmount(double debitGreaterthanAmount) {
        this.debitGreaterthanAmount = debitGreaterthanAmount;
    }

    public double getStdCC() {
        return stdCC;
    }

    public void setStdCC(double stdCC) {
        this.stdCC = stdCC;
    }

    public double getPremiumCC() {
        return premiumCC;
    }

    public void setPremiumCC(double premiumCC) {
        this.premiumCC = premiumCC;
    }

    public double getCorpCC() {
        return corpCC;
    }

    public void setCorpCC(double corpCC) {
        this.corpCC = corpCC;
    }

    public double getIntlCC() {
        return intlCC;
    }

    public void setIntlCC(double intlCC) {
        this.intlCC = intlCC;
    }

    public double getSuperPremiumlCC() {
        return superPremiumlCC;
    }

    public void setSuperPremiumlCC(double superPremiumlCC) {
        this.superPremiumlCC = superPremiumlCC;
    }

    public String getPosEnable() {
        return posEnable;
    }

    public void setPosEnable(String posEnable) {
        this.posEnable = posEnable;
    }

    public int getCmsLeadStatus() {
        return cmsLeadStatus;
    }

    public void setCmsLeadStatus(int cmsLeadStatus) {
        this.cmsLeadStatus = cmsLeadStatus;
    }

    public double getSetUpFeeAppliedAmount() {
        return setUpFeeAppliedAmount;
    }

    public void setSetUpFeeAppliedAmount(double setUpFeeAppliedAmount) {
        this.setUpFeeAppliedAmount = setUpFeeAppliedAmount;
    }

    public double getRecurringFeeAppliedAmount() {
        return recurringFeeAppliedAmount;
    }

    public void setRecurringFeeAppliedAmount(double recurringFeeAppliedAmount) {
        this.recurringFeeAppliedAmount = recurringFeeAppliedAmount;
    }

    public double getAppliedDebitLessthanAmount() {
        return appliedDebitLessthanAmount;
    }

    public void setAppliedDebitLessthanAmount(double appliedDebitLessthanAmount) {
        this.appliedDebitLessthanAmount = appliedDebitLessthanAmount;
    }

    public double getAppliedDebitGreaterthanAmount() {
        return appliedDebitGreaterthanAmount;
    }

    public void setAppliedDebitGreaterthanAmount(double appliedDebitGreaterthanAmount) {
        this.appliedDebitGreaterthanAmount = appliedDebitGreaterthanAmount;
    }

    public double getAppliedStdCC() {
        return appliedStdCC;
    }

    public void setAppliedStdCC(double appliedStdCC) {
        this.appliedStdCC = appliedStdCC;
    }

    public double getAppliedPremiumCC() {
        return appliedPremiumCC;
    }

    public void setAppliedPremiumCC(double appliedPremiumCC) {
        this.appliedPremiumCC = appliedPremiumCC;
    }

    public double getAppliedCorpCC() {
        return appliedCorpCC;
    }

    public void setAppliedCorpCC(double appliedCorpCC) {
        this.appliedCorpCC = appliedCorpCC;
    }

    public double getAppliedIntlCC() {
        return appliedIntlCC;
    }

    public void setAppliedIntlCC(double appliedIntlCC) {
        this.appliedIntlCC = appliedIntlCC;
    }

    public double getAppliedSuperPremiumCC() {
        return appliedSuperPremiumCC;
    }

    public void setAppliedSuperPremiumCC(double appliedSuperPremiumCC) {
        this.appliedSuperPremiumCC = appliedSuperPremiumCC;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public User getAssignedToNH() {
        return assignedToNH;
    }

    public void setAssignedToNH(User assignedToNH) {
        this.assignedToNH = assignedToNH;
    }

    public boolean isMdrSubventionRequested() {
        return isMdrSubventionRequested;
    }

    public void setMdrSubventionRequested(boolean mdrSubventionRequested) {
        isMdrSubventionRequested = mdrSubventionRequested;
    }

    public boolean isRentalSubventionRequested() {
        return isRentalSubventionRequested;
    }

    public void setRentalSubventionRequested(boolean rentalSubventionRequested) {
        isRentalSubventionRequested = rentalSubventionRequested;
    }

    public int getLeadCategory() {
        return leadCategory;
    }

    public void setLeadCategory(int leadCategory) {
        this.leadCategory = leadCategory;
    }
}
