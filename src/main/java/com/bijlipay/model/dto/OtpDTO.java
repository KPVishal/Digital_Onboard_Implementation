package com.bijlipay.model.dto;

public class OtpDTO {
    private String cmd;
    private String merchantVerify;
    //new changes digi
    private String otp;


    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getMerchantVerify() {
        return merchantVerify;
    }

    public void setMerchantVerify(String merchantVerify) {
        this.merchantVerify = merchantVerify;
    }


    //new changes digi
    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }
}
