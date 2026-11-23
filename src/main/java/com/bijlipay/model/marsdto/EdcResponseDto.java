package com.bijlipay.model.marsdto;

public class EdcResponseDto {

    private Long merchantRefCode;
    private String applicationNumber;
    private String merchantId;
    private String terminalId;
    private String remarks;

    private String agrName;

    private String vpa;
    private String responseCode;
    private boolean flag;

    public String getAgrName() {
        return agrName;
    }

    public void setAgrName(String agrName) {
        this.agrName = agrName;
    }

    public Long getMerchantRefCode() {
        return merchantRefCode;
    }

    public void setMerchantRefCode(Long merchantRefCode) {
        this.merchantRefCode = merchantRefCode;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
