package com.bijlipay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subvention_logs")
public class SubventionLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subvention_log_id", nullable = false)
    private long id;

    @Column(name = "subvention_request_date",nullable = true)
    private Date subventionReqDate;

    @ManyToOne(optional = true)
    @JoinColumn(name = "lead_sub_status", referencedColumnName = "lead_sub_status_id")
    private LeadSubStatus leadSubStatus;

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "lead_id", referencedColumnName = "lead_id")
    private LeadInformation leadInformation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getSubventionReqDate() {
        return subventionReqDate;
    }

    public void setSubventionReqDate(Date subventionReqDate) {
        this.subventionReqDate = subventionReqDate;
    }

    public LeadSubStatus getLeadSubStatus() {
        return leadSubStatus;
    }

    public void setLeadSubStatus(LeadSubStatus leadSubStatus) {
        this.leadSubStatus = leadSubStatus;
    }

    public LeadInformation getLeadInformation() {
        return leadInformation;
    }

    public void setLeadInformation(LeadInformation leadInformation) {
        this.leadInformation = leadInformation;
    }


}
