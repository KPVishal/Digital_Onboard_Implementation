package com.bijlipay.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "merchant_document_type")
public class MerchantDocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_type_id", nullable = false)
    private long id;

    @Column(name = "document_type", nullable = true)
    private String documentType;

    @Column(name = "is_active", columnDefinition = "tinyint(1) default 1", nullable = true)
    private boolean isActive;

    @Column(name = "category_description", nullable = true)
    private String categoryDescription;

    @Column(name = "upload_any_one", nullable = true)
    private int uploadAnyOne; // 1- Anyone 2- All Required 3- Mandatory

    @ManyToOne(optional = true)
    @JoinColumn(name = "merchant_type", referencedColumnName = "merchant_type_id", nullable = true)
    private MerchantType merchantType;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private Date updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public int getUploadAnyOne() {
        return uploadAnyOne;
    }

    public void setUploadAnyOne(int uploadAnyOne) {
        this.uploadAnyOne = uploadAnyOne;
    }

    public MerchantType getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(MerchantType merchantType) {
        this.merchantType = merchantType;
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
        return "MerchantDocumentType{" +
                "id=" + id +
                ", documentType='" + documentType + '\'' +
                ", isActive=" + isActive +
                ", categoryDescription='" + categoryDescription + '\'' +
                ", uploadAnyOne=" + uploadAnyOne +
                ", merchantType=" + merchantType +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
