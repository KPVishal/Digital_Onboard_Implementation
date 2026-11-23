package com.bijlipay.model.dto;

public class CounterManagementRequest {

        private String sim;
        private String deviceSerialNo;
        private String deviceType;
        private String soundBoxSno;
        private String vpa;

        private String networkType;

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getDeviceSerialNo() {
        return deviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
        this.deviceSerialNo = deviceSerialNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getSoundBoxSno() {
        return soundBoxSno;
    }

    public void setSoundBoxSno(String soundBoxSno) {
        this.soundBoxSno = soundBoxSno;
    }
}
