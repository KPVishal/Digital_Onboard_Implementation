package com.bijlipay.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "merchant_types")
public class MerchantType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merchant_type_id")
    private int id;

    @Column(name = "merchant_type_name")
    private String merchantTypeName;

    @Column(name = "is_active", columnDefinition = "tinyint(1) default 1")
    private boolean isActive;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private Date updatedDate;
    @Column(name = "merchant_type_code")
    private String merchantTypeCode;

    public MerchantType(){

    }

    public MerchantType(int id, String merchantTypeName, boolean isActive, Date createDate, Date updatedDate) {
        this.id = id;
        this.merchantTypeName = merchantTypeName;
        this.isActive = isActive;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMerchantTypeName() {
        return merchantTypeName;
    }

    public void setMerchantTypeName(String merchantTypeName) {
        this.merchantTypeName = merchantTypeName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getMerchantTypeCode() {
        return merchantTypeCode;
    }

    public void setMerchantTypeCode(String merchantTypeCode) {
        this.merchantTypeCode = merchantTypeCode;
    }

    @Override
    public String toString() {
        return "MerchantType{" +
                "id=" + id +
                ", merchantTypeName='" + merchantTypeName + '\'' +
                ", isActive=" + isActive +
                ", createDate=" + createDate +
                ", updatedDate=" + updatedDate +
                ", merchantTypeCode='" + merchantTypeCode + '\'' +
                '}';
    }
}
