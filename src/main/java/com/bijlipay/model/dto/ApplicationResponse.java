package com.bijlipay.model.dto;

import java.util.List;

public class ApplicationResponse {

        private String applicationNumber;
        private String merchantRefCode;
        private List<TerminalDetails> data;
        private String remarks;
        private String agrName;
        private String responseCode;
        private String institutionCode;

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getMerchantRefCode() {
        return merchantRefCode;
    }

    public void setMerchantRefCode(String merchantRefCode) {
        this.merchantRefCode = merchantRefCode;
    }


    public List<TerminalDetails> getData() {
        return data;
    }

    public void setData(List<TerminalDetails> data) {
        this.data = data;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAgrName() {
        return agrName;
    }

    public void setAgrName(String agrName) {
        this.agrName = agrName;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    @Override
    public String toString() {
        return "ApplicationResponse{" +
                "applicationNumber='" + applicationNumber + '\'' +
                ", merchantRefCode='" + merchantRefCode + '\'' +
                ", data=" + data +
                ", remarks='" + remarks + '\'' +
                ", agrName='" + agrName + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", institutionCode='" + institutionCode + '\'' +
                '}';
    }
}
