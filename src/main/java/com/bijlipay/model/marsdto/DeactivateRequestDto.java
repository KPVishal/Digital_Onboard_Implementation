package com.bijlipay.model.marsdto;

public class DeactivateRequestDto {
    private String insCode;
    private String merchantId;
    private String terminalId;
    private String remark;
    private String applicationNo;

    public String getInsCode() {
        return insCode;
    }

    public void setInsCode(String insCode) {
        this.insCode = insCode;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }
}
