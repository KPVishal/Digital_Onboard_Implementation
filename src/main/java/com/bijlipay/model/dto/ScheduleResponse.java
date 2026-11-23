package com.bijlipay.model.dto;

import com.bijlipay.model.LeadInformation;

import java.util.Date;

public class ScheduleResponse {

    private Date scheduleDate;
    private Date reScheduleDate;

    private String rescheduleRemarks;

    private String rescheduleReason;

    private LeadInformation leadInformation;

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Date getReScheduleDate() {
        return reScheduleDate;
    }

    public void setReScheduleDate(Date reScheduleDate) {
        this.reScheduleDate = reScheduleDate;
    }

    public String getRescheduleRemarks() {
        return rescheduleRemarks;
    }

    public void setRescheduleRemarks(String rescheduleRemarks) {
        this.rescheduleRemarks = rescheduleRemarks;
    }

    public String getRescheduleReason() {
        return rescheduleReason;
    }

    public void setRescheduleReason(String rescheduleReason) {
        this.rescheduleReason = rescheduleReason;
    }

    public LeadInformation getLeadInformation() {
        return leadInformation;
    }

    public void setLeadInformation(LeadInformation leadInformation) {
        this.leadInformation = leadInformation;
    }
}
