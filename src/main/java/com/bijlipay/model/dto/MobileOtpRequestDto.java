package com.bijlipay.model.dto;

public class MobileOtpRequestDto {

    private long leadId;
    private String email;
    private String mobileNumber;
    private String otp;

    // Default Constructor
    public MobileOtpRequestDto() {}

    // Getters and Setters
    public long getLeadId() {
        return leadId;
    }

    public void setLeadId(long leadId) {
        this.leadId = leadId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }


}
