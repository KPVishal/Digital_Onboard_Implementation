package com.bijlipay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lead_documents")
public class LeadDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lead_document_id", nullable = false)
    private int leadDocumentId;

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "lead_id", referencedColumnName = "lead_id")
    private LeadInformation leadInformation;

    @ManyToOne(optional = true)
    @JoinColumn(name = "sub_document_type", referencedColumnName = "sub_document_type_id")
    private SubDocumentType subDocumentType;

    @Column(name = "document_verified_status", nullable = true)
    private int documentVerifiedStatus; //lead document status from constants

    @Column(name = "is_reprocess", nullable = true)
    private boolean isReprocess;

    @Column(name = "document_remarks", nullable = true)
    private String documentRemarks;

    @ManyToOne(optional = true)
    @JoinColumn(name = "document_cancel_reason", referencedColumnName = "reason_id")
    private LeadCancellationReason leadCancellationReason;

    @ManyToOne(optional = true)
    @JoinColumn(name = "uploaded_by", referencedColumnName = "user_id")
    private User uploadedBy;

    @ManyToOne(optional = true)
    @JoinColumn(name = "rejected_by", referencedColumnName = "user_id")
    private User rejectedBy;

    @Column(name = "approved_date", nullable = true)
    private Date approvedDate;

    @Column(name = "rejected_date", nullable = true)
    private Date rejectedDate;

    @ManyToOne(optional = true)
    @JoinColumn(name = "so_cancel_reason", referencedColumnName = "reason_id")
    private LeadCancellationReason soCancelReason;

    @Column(name = "so_cancel_remarks", nullable = true)
    private String soCancelRemarks;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(optional = true)
    @JoinColumn(name = "new_document", referencedColumnName = "mapping_id", nullable = true)
    private DocumentMapping documentMapping;

    public int getLeadDocumentId() {
        return leadDocumentId;
    }

    public void setLeadDocumentId(int leadDocumentId) {
        this.leadDocumentId = leadDocumentId;
    }

    public LeadInformation getLeadInformation() {
        return leadInformation;
    }

    public void setLeadInformation(LeadInformation leadInformation) {
        this.leadInformation = leadInformation;
    }

    public SubDocumentType getSubDocumentType() {
        return subDocumentType;
    }

    public void setSubDocumentType(SubDocumentType subDocumentType) {
        this.subDocumentType = subDocumentType;
    }

    public int getDocumentVerifiedStatus() {
        return documentVerifiedStatus;
    }

    public void setDocumentVerifiedStatus(int documentVerifiedStatus) {
        this.documentVerifiedStatus = documentVerifiedStatus;
    }

    public boolean isReprocess() {
        return isReprocess;
    }

    public void setReprocess(boolean reprocess) {
        isReprocess = reprocess;
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

    public String getDocumentRemarks() {
        return documentRemarks;
    }

    public void setDocumentRemarks(String documentRemarks) {
        this.documentRemarks = documentRemarks;
    }

    public LeadCancellationReason getLeadCancellationReason() {
        return leadCancellationReason;
    }

    public void setLeadCancellationReason(LeadCancellationReason leadCancellationReason) {
        this.leadCancellationReason = leadCancellationReason;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public User getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(User rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Date getRejectedDate() {
        return rejectedDate;
    }

    public void setRejectedDate(Date rejectedDate) {
        this.rejectedDate = rejectedDate;
    }

    public LeadCancellationReason getSoCancelReason() {
        return soCancelReason;
    }

    public void setSoCancelReason(LeadCancellationReason soCancelReason) {
        this.soCancelReason = soCancelReason;
    }

    public String getSoCancelRemarks() {
        return soCancelRemarks;
    }

    public void setSoCancelRemarks(String soCancelRemarks) {
        this.soCancelRemarks = soCancelRemarks;
    }

    public DocumentMapping getDocumentMapping() {
        return documentMapping;
    }

    public void setDocumentMapping(DocumentMapping documentMapping) {
        this.documentMapping = documentMapping;
    }

    @Override
    public String toString() {
        return "LeadDocuments{" +
                "leadDocumentId=" + leadDocumentId +
                ", leadInformation=" + leadInformation +
                ", subDocumentType=" + subDocumentType +
                ", documentVerifiedStatus=" + documentVerifiedStatus +
                ", isReprocess=" + isReprocess +
                ", documentRemarks='" + documentRemarks + '\'' +
                ", leadCancellationReason=" + leadCancellationReason +
                ", uploadedBy=" + uploadedBy +
                ", rejectedBy=" + rejectedBy +
                ", approvedDate=" + approvedDate +
                ", rejectedDate=" + rejectedDate +
                ", soCancelReason=" + soCancelReason +
                ", soCancelRemarks='" + soCancelRemarks + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", documentMapping=" + documentMapping +
                '}';
    }
}
