package com.bijlipay.model.dto;

import java.util.List;

public class DeactivateDto {
    private List<String> vpa;
    private  String device;
    private String serialNumber;

    public List<String> getVpa() {
        return vpa;
    }

    public void setVpa(List<String> vpa) {
        this.vpa = vpa;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
