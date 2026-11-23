package com.bijlipay.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private long id;

    @Column(name = "device_name", nullable = false, unique = true)
    private String deviceName;

    @Column(name = "is_active", columnDefinition = "tinyint(1) default 1")
    private boolean isActive;

    @Column(name = "color_code")
    private String colorCode;

    @Column(name = "serial_number_length", columnDefinition = "int(11) default 0")
    private int serialNumberLength;

    @Column(name = "mars_device_code")
    private String marsDeviceCode;

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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public int getSerialNumberLength() {
        return serialNumberLength;
    }

    public void setSerialNumberLength(int serialNumberLength) {
        this.serialNumberLength = serialNumberLength;
    }

    public String getMarsDeviceCode() {
        return marsDeviceCode;
    }

    public void setMarsDeviceCode(String marsDeviceCode) {
        this.marsDeviceCode = marsDeviceCode;
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
        return "Device{" +
                "id=" + id +
                ", deviceName='" + deviceName + '\'' +
                ", isActive=" + isActive +
                ", colorCode='" + colorCode + '\'' +
                ", serialNumberLength=" + serialNumberLength +
                ", marsDeviceCode='" + marsDeviceCode + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
