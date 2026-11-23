package com.bijlipay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "counter_management")
public class CounterManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "count_id", nullable = false)
    private long id;

    @Column(name = "counter_no",nullable = true)
    private int counterNo;

    @Column(name = "sim", nullable = true)
    private String sim;


    @Column(name = "network_type", nullable = true)
    private String networkType;

    @Column(name = "vpa_list",nullable = true)
    private String vpa;

    @Column(name = "soundBox_serialNo",nullable = true)
    private String soundBoxSno;

    @Column(name = "device_serial_no",nullable = true)
    private String deviceSerialNo;
    @Column(name = "device_type", nullable = true)
    private String deviceType;
    @Column(name = "tid_pos",nullable = true)
    private String tid;
    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "lead_id", referencedColumnName = "lead_id")
    private LeadInformation leadInformation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(int counterNo) {
        this.counterNo = counterNo;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }

    public String getSoundBoxSno() {
        return soundBoxSno;
    }

    public void setSoundBoxSno(String soundBox_sno) {
        this.soundBoxSno = soundBox_sno;
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

    public LeadInformation getLeadInformation() {
        return leadInformation;
    }

    public void setLeadInformation(LeadInformation leadInformation) {
        this.leadInformation = leadInformation;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "CounterManagement{" +
                "id=" + id +
                ", counterNo=" + counterNo +
                ", sim='" + sim + '\'' +
                ", networkType='" + networkType + '\'' +
                ", vpa='" + vpa + '\'' +
                ", soundBoxSno='" + soundBoxSno + '\'' +
                ", deviceSerialNo='" + deviceSerialNo + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", tid='" + tid + '\'' +
                ", leadInformation=" + leadInformation +
                '}';
    }
}

