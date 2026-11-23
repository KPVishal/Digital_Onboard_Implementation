package com.bijlipay.model.dto;


import java.util.Date;

public class MarsOnboardInfoDto {
    private String createdBy;
    private String updatedBy;
    private String applicationNumber;
    private Integer merchantRefCode;
    private String salesInformation;
    private String companyInformation;
    private String businessInformation;
    private String partnerInformation;
    private String paymentDetails;
    private String bankInformation;
    private String additionalInfo;
    private String customIncentiveRates;
    private String mcc;
    private String mdrPlan;
    private String SharingDiscountFee;
    private String holdPayment;


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Integer getMerchantRefCode() {
        return merchantRefCode;
    }

    public void setMerchantRefCode(Integer merchantRefCode) {
        this.merchantRefCode = merchantRefCode;
    }

    public String getSalesInformation() {
        return salesInformation;
    }

    public void setSalesInformation(String salesInformation) {
        this.salesInformation = salesInformation;
    }

    public String getCompanyInformation() {
        return companyInformation;
    }

    public void setCompanyInformation(String companyInformation) {
        this.companyInformation = companyInformation;
    }

    public String getBusinessInformation() {
        return businessInformation;
    }

    public void setBusinessInformation(String businessInformation) {
        this.businessInformation = businessInformation;
    }

    public String getPartnerInformation() {
        return partnerInformation;
    }

    public void setPartnerInformation(String partnerInformation) {
        this.partnerInformation = partnerInformation;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getBankInformation() {
        return bankInformation;
    }

    public void setBankInformation(String bankInformation) {
        this.bankInformation = bankInformation;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getCustomIncentiveRates() {
        return customIncentiveRates;
    }

    public void setCustomIncentiveRates(String customIncentiveRates) {
        this.customIncentiveRates = customIncentiveRates;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMdrPlan() {
        return mdrPlan;
    }

    public void setMdrPlan(String mdrPlan) {
        this.mdrPlan = mdrPlan;
    }

    public String getSharingDiscountFee() {
        return SharingDiscountFee;
    }

    public void setSharingDiscountFee(String sharingDiscountFee) {
        SharingDiscountFee = sharingDiscountFee;
    }

    public String getHoldPayment() {
        return holdPayment;
    }

    public void setHoldPayment(String holdPayment) {
        this.holdPayment = holdPayment;
    }

}
