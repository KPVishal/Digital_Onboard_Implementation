package com.bijlipay.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "document_mapping")
public class DocumentMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id", nullable = false)
    private long id;

    @Column(name = "document_name", nullable = true)
    private String documentName;

    @ManyToOne(optional = true)
    @JoinColumn(name = "merchant_type", referencedColumnName = "merchant_type_id", nullable = true)
    private MerchantType merchantType;

    @ManyToOne(optional = true)
    @JoinColumn(name = "merchant_document_type", referencedColumnName = "document_type_id", nullable = true)
    private MerchantDocumentType merchantDocumentType;

    @ManyToOne(optional = true)
    @JoinColumn(name = "document", referencedColumnName = "document_id", nullable = true)
    private Document document;

    @Column(name = "is_signzy_verification", columnDefinition = "tinyint(1) default 0")
    private boolean isSignzyVerification;

    @Column(name = "is_active", columnDefinition = "tinyint(1) default 1")
    private boolean isActive;

    @Column(name = "is_flag", columnDefinition = "tinyint(1) default 1")
    private boolean isFlag; // 1- Mandatory 2- NonMandatory

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private Date updatedAt;

//    @Transient
//    private UploadedDocumentsDto uploadedDocuments;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public MerchantType getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(MerchantType merchantType) {
        this.merchantType = merchantType;
    }

    public MerchantDocumentType getMerchantDocumentType() {
        return merchantDocumentType;
    }

    public void setMerchantDocumentType(MerchantDocumentType merchantDocumentType) {
        this.merchantDocumentType = merchantDocumentType;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public boolean isSignzyVerification() {
        return isSignzyVerification;
    }

    public void setSignzyVerification(boolean signzyVerification) {
        isSignzyVerification = signzyVerification;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
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

    @Override
    public String toString() {
        return "DocumentMapping{" +
                "id=" + id +
                ", documentName='" + documentName + '\'' +
                ", merchantType=" + merchantType +
                ", merchantDocumentType=" + merchantDocumentType +
                ", document=" + document +
                ", isSignzyVerification=" + isSignzyVerification +
                ", isActive=" + isActive +
                ", isFlag=" + isFlag +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
