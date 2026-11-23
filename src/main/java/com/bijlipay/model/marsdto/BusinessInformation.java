package com.bijlipay.model.marsdto;

import java.util.Date;

public class BusinessInformation {

    private double averageBillAmount;
    private String businessIncome;
    private String creditCardMdr;
    private String currentPosName;
    private String debitCardMdr;
    private String expectedCardBusiness;
    private String lastTurnoverAmount;
    private String lastTurnoverYear;
    private String memberSince;
    private String weekdayEndHour;
    private String weekdayStartHour;
    private String weekendEndHour;
    private String weekendStartHour;
    private String gstId;
    private String maximumMonthlyUsage;
    private String merchantTypeCode;

    public String getMerchantTypeCode() {
        return merchantTypeCode;
    }

    public void setMerchantTypeCode(String merchantTypeCode) {
        this.merchantTypeCode = merchantTypeCode;
    }

    public String getMaximumMonthlyUsage() {
        return maximumMonthlyUsage;
    }

    public void setMaximumMonthlyUsage(String maximumMonthlyUsage) {
        this.maximumMonthlyUsage = maximumMonthlyUsage;
    }

    public String getGstId() {
        return gstId;
    }

    public void setGstId(String gstId) {
        this.gstId = gstId;
    }

    public double getAverageBillAmount() {
        return averageBillAmount;
    }

    public void setAverageBillAmount(double averageBillAmount) {
        this.averageBillAmount = averageBillAmount;
    }

    public String getBusinessIncome() {
        return businessIncome;
    }

    public void setBusinessIncome(String businessIncome) {
        this.businessIncome = businessIncome;
    }

    public String getCreditCardMdr() {
        return creditCardMdr;
    }

    public void setCreditCardMdr(String creditCardMdr) {
        this.creditCardMdr = creditCardMdr;
    }

    public String getCurrentPosName() {
        return currentPosName;
    }

    public void setCurrentPosName(String currentPosName) {
        this.currentPosName = currentPosName;
    }

    public String getDebitCardMdr() {
        return debitCardMdr;
    }

    public void setDebitCardMdr(String debitCardMdr) {
        this.debitCardMdr = debitCardMdr;
    }

    public String getExpectedCardBusiness() {
        return expectedCardBusiness;
    }

    public void setExpectedCardBusiness(String expectedCardBusiness) {
        this.expectedCardBusiness = expectedCardBusiness;
    }

    public String getLastTurnoverAmount() {
        return lastTurnoverAmount;
    }

    public void setLastTurnoverAmount(String lastTurnoverAmount) {
        this.lastTurnoverAmount = lastTurnoverAmount;
    }

    public String getLastTurnoverYear() {
        return lastTurnoverYear;
    }

    public void setLastTurnoverYear(String lastTurnoverYear) {
        this.lastTurnoverYear = lastTurnoverYear;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public String getWeekdayEndHour() {
        return weekdayEndHour;
    }

    public void setWeekdayEndHour(String weekdayEndHour) {
        this.weekdayEndHour = weekdayEndHour;
    }

    public String getWeekdayStartHour() {
        return weekdayStartHour;
    }

    public void setWeekdayStartHour(String weekdayStartHour) {
        this.weekdayStartHour = weekdayStartHour;
    }

    public String getWeekendEndHour() {
        return weekendEndHour;
    }

    public void setWeekendEndHour(String weekendEndHour) {
        this.weekendEndHour = weekendEndHour;
    }

    public String getWeekendStartHour() {
        return weekendStartHour;
    }

    public void setWeekendStartHour(String weekendStartHour) {
        this.weekendStartHour = weekendStartHour;
    }
}
