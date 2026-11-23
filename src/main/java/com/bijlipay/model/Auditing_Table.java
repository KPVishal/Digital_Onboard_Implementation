package com.bijlipay.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "auditing_table")
public class Auditing_Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditing_id", nullable = false)
    private long id;

    @Column(name = "action",nullable = true)
    private String action;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "request_date",nullable = true)
    private Date requestDate;

    @Column(name = "updated_date",nullable = true)
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "user",referencedColumnName = "user_id")
    private User user;
    @Column(name = "lead_id", nullable = false)
    private long leadId;
    @ManyToOne(optional = true)
    @JoinColumn(name = "mcc_requested", referencedColumnName = "mcc_id")
    private MerchantCategoryCode mccRequested;
    @ManyToOne(optional = true)
    @JoinColumn(name = "mcc_approved", referencedColumnName = "mcc_id")
    private MerchantCategoryCode mccApproved;
    @Column(name = "remark", nullable = true)
    private String remark;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getLeadId() {
        return leadId;
    }

    public void setLeadId(long leadId) {
        this.leadId = leadId;
    }


    public MerchantCategoryCode getMccRequested() {
        return mccRequested;
    }

    public void setMccRequested(MerchantCategoryCode mccRequested) {
        this.mccRequested = mccRequested;
    }

    public MerchantCategoryCode getMccApproved() {
        return mccApproved;
    }

    public void setMccApproved(MerchantCategoryCode mccApproved) {
        this.mccApproved = mccApproved;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
