package com.bijlipay.model.marsdto;


import com.bijlipay.model.dto.TerminalDetail;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class MarsDto {

    private String device;
    private String remarks;
    private JSONObject salesInformation;
    private JSONObject additionalInfo;
    private JSONObject companyInformation;
    private JSONObject businessInformation;
    private JSONArray partnerInformation;
    private List<CustomIncentiveRatesDto> customIncentiveRates;
    private PaymentDetailsDto paymentDetails;
    private List<TerminalDetail> TerminalDetails;
    private JSONObject bankInformation;
    private MdrPlanDto mdrPlan;
    private JSONObject revParameters;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public JSONObject getSalesInformation() {
        return salesInformation;
    }

    public void setSalesInformation(JSONObject salesInformation) {
        this.salesInformation = salesInformation;
    }

    public JSONObject getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(JSONObject additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public JSONObject getCompanyInformation() {
        return companyInformation;
    }

    public void setCompanyInformation(JSONObject companyInformation) {
        this.companyInformation = companyInformation;
    }

    public JSONObject getBusinessInformation() {
        return businessInformation;
    }

    public void setBusinessInformation(JSONObject businessInformation) {
        this.businessInformation = businessInformation;
    }

    public JSONArray getPartnerInformation() {
        return partnerInformation;
    }

    public void setPartnerInformation(JSONArray partnerInformation) {
        this.partnerInformation = partnerInformation;
    }

    public List<CustomIncentiveRatesDto> getCustomIncentiveRates() {
        return customIncentiveRates;
    }

    public void setCustomIncentiveRates(List<CustomIncentiveRatesDto> customIncentiveRates) {
        this.customIncentiveRates = customIncentiveRates;
    }

    public PaymentDetailsDto getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetailsDto paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public JSONObject getBankInformation() {
        return bankInformation;
    }

    public void setBankInformation(JSONObject bankInformation) {
        this.bankInformation = bankInformation;
    }

    public MdrPlanDto getMdrPlan() {
        return mdrPlan;
    }

    public void setMdrPlan(MdrPlanDto mdrPlan) {
        this.mdrPlan = mdrPlan;
    }

    public JSONObject getRevParameters() {
        return revParameters;
    }

    public void setRevParameters(JSONObject revParameters) {
        this.revParameters = revParameters;
    }

    public List<TerminalDetail> getTerminalDetails() {
        return TerminalDetails;
    }

    public void setTerminalDetails(List<TerminalDetail> terminalDetails) {
        TerminalDetails = terminalDetails;
    }

    @Override
    public String toString() {
        return "MarsDto{" +
                "device='" + device + '\'' +
                ", remarks='" + remarks + '\'' +
                ", salesInformation=" + salesInformation +
                ", additionalInfo=" + additionalInfo +
                ", companyInformation=" + companyInformation +
                ", businessInformation=" + businessInformation +
                ", partnerInformation=" + partnerInformation +
                ", customIncentiveRates=" + customIncentiveRates +
                ", paymentDetails=" + paymentDetails +
                ", bankInformation=" + bankInformation +
                ", mdrPlan=" + mdrPlan +
                ", revParameters=" + revParameters +
                '}';
    }
}
