package com.bijlipay.model.marsdto;

public class SavingToTable {

    private Integer merchantRefCode;
    private String applicationNumber;
    private String merchantId;
    private String terminalId;
//    private String agrName;

//    public String getAgrName() {
//        return agrName;
//    }
//
//    public void setAgrName(String agrName) {
//        this.agrName = agrName;
//    }
private String vpa;

    public Integer getMerchantRefCode() {
        return merchantRefCode;
    }

    public void setMerchantRefCode(Integer merchantRefCode) {
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

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }
}
