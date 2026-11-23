package com.bijlipay.model.dto;

import com.bijlipay.model.Device;
import com.bijlipay.model.User;

public class DeviceDto {

    private String serialNumber;

    private String deviceName;

//    private Device device;
//    private User user;
    private String tid;
    private String mid;
    private String applicationNumber;
    private String implStateName;
    private String implDistrictName;
    private String implSubDistrictName;
    private String implVillageName;
    private String implTier;
    private String implLatLng;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    //    public Device getDevice() {
//        return device;
//    }
//
//    public void setDevice(Device device) {
//        this.device = device;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getImplStateName() {
        return implStateName;
    }

    public void setImplStateName(String implStateName) {
        this.implStateName = implStateName;
    }

    public String getImplDistrictName() {
        return implDistrictName;
    }

    public void setImplDistrictName(String implDistrictName) {
        this.implDistrictName = implDistrictName;
    }

    public String getImplSubDistrictName() {
        return implSubDistrictName;
    }

    public void setImplSubDistrictName(String implSubDistrictName) {
        this.implSubDistrictName = implSubDistrictName;
    }

    public String getImplVillageName() {
        return implVillageName;
    }

    public void setImplVillageName(String implVillageName) {
        this.implVillageName = implVillageName;
    }

    public String getImplTier() {
        return implTier;
    }

    public void setImplTier(String implTier) {
        this.implTier = implTier;
    }

    public String getImplLatLng() {
        return implLatLng;
    }

    public void setImplLatLng(String implLatLng) {
        this.implLatLng = implLatLng;
    }
}
