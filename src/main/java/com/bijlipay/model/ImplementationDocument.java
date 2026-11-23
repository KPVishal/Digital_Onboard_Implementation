package com.bijlipay.model;

import javax.persistence.*;

@Entity
@Table(name = "implementation_document")
public class ImplementationDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name="lead_id",nullable = true)
    private long leadId;

    @Column(name = "implmentation_form_flag",columnDefinition = "tinyint(1) default 0",nullable = false)
    private int formFlag;

    @Column(name="implementation_form_picture",nullable = true)
    private String implementationFormPicture;
    @Column(name = "merchant_name",nullable = true)
    private String merchantName;
    @Column(name = "ip_address",nullable = true)
    private String ipAddress;
    @Column(name = "device_name",nullable = true)
    private String deviceName;
    @Column(name = "lat_long",nullable = true)
    private String latLong;
    @Column(name = "date_time",nullable = true)
    private String dateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLeadId() {
        return leadId;
    }

    public void setLeadId(long leadId) {
        this.leadId = leadId;
    }

    public int getFormFlag() {
        return formFlag;
    }

    public void setFormFlag(int formFlag) {
        this.formFlag = formFlag;
    }

    public String getImplementationFormPicture() {
        return implementationFormPicture;
    }

    public void setImplementationFormPicture(String implementationFormPicture) {
        this.implementationFormPicture = implementationFormPicture;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
