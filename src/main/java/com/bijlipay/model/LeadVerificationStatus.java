package com.bijlipay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lead_verification_status")
public class LeadVerificationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lead_verification_id", nullable = false)
    private long id;

    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @Column(name = "reasonType", nullable = true)
    private String reasonType;

    @Column(name = "reason", nullable = true, length = 4096)
    private String reason;

    @Column(name = "verification_type", columnDefinition = "int(11) default 0")
    private Integer verificationType; // 1-device, 2-document, 3-kyc, 4-pricing, 5-banksubvention, 6-Finance, 7-all

    @Column(name = "lead_category", nullable = true)
    private Integer leadCategory; // 1-normal, 2-exception

    @Column(name = "is_active", columnDefinition = "tinyint(1) default 1")
    private boolean status; // 0-reject, 1-approved

    // this is based on kyc
    @Column(name = "expected_submit_date", nullable = true)
    private Date expectedSubmitDate;

    @Column(name = "kyc_sat_remark", nullable = true)
    private String kycSatRemark;

    // this is based on bank subvention
    @Column(name = "bank_attached_file", nullable = true)
    private String bankAttachedFile;

    @Column(name = "mime_type", nullable = true)
    private String mimeType;

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "lead_id",referencedColumnName = "lead_id")
    private LeadInformation leadInformation;

    @ManyToOne(optional = true)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;

    @ManyToOne(optional = true)
    @JoinColumn(name = "updated_by", referencedColumnName = "user_id")
    private User updatedBy;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(optional = true)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(Integer verificationType) {
        this.verificationType = verificationType;
    }

    public Integer getLeadCategory() {
        return leadCategory;
    }

    public void setLeadCategory(Integer leadCategory) {
        this.leadCategory = leadCategory;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getExpectedSubmitDate() {
        return expectedSubmitDate;
    }

    public void setExpectedSubmitDate(Date expectedSubmitDate) {
        this.expectedSubmitDate = expectedSubmitDate;
    }

    public String getKycSatRemark() {
        return kycSatRemark;
    }

    public void setKycSatRemark(String kycSatRemark) {
        this.kycSatRemark = kycSatRemark;
    }

    public String getBankAttachedFile() {
        return bankAttachedFile;
    }

    public void setBankAttachedFile(String bankAttachedFile) {
        this.bankAttachedFile = bankAttachedFile;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public LeadInformation getLeadInformation() {
        return leadInformation;
    }

    public void setLeadInformation(LeadInformation leadInformation) {
        this.leadInformation = leadInformation;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
