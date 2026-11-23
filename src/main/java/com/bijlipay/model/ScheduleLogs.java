package com.bijlipay.model;


import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "schedule_logs")
public class ScheduleLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", nullable = false)
    private long id;

    @Column(name = "schedule_date",nullable = true)
    private Date scheduleDate;

    @Column(name = "reschdule_remarks",nullable = true)
    private String rescheduleRemarks;

    @Column(name = "reschdule_reason",nullable = true)
    private String rescheduleReason;



    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "lead_id", referencedColumnName = "lead_id")
    private LeadInformation leadInformation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
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

    @Override
    public String toString() {
        return "ScheduleLogs{" +
                "id=" + id +
                ", scheduleDate=" + scheduleDate +
                ", rescheduleRemarks='" + rescheduleRemarks + '\'' +
                ", rescheduleReason='" + rescheduleReason + '\'' +
                ", leadInformation=" + leadInformation +
                '}';
    }
}
