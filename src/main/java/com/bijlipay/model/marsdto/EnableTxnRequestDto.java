package com.bijlipay.model.marsdto;

public class EnableTxnRequestDto {
    private String applicationNumber;

    private int institutionCode;

    private String mid;

    private String remark;

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public int getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(int institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
