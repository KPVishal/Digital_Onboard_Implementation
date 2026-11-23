package com.bijlipay.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.web.JsonPath;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ConsentVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "lead_id", referencedColumnName = "lead_id")
    private LeadInformation LeadId;
    private String ipAddress;
    private String lat;
    private String lang;
    private Date systemDate;
    private Date localDate;
    private boolean verified;
    private String ipAddressPh;
    private String latPh;
    private String langPn;
    private Date systemDatePh;
    private Date localDatePh;
    private String ipAddressEmail;
    private String latEmail;
    private String langEmail;
    private Date systemDateEmail;
    private Date localDateEmail;
    private String ipAddress2;
    private String lat2;
    private String lang2;
    private Date systemDate2;
    private Date localDate2;

    private boolean verifiedPh;
    private boolean verifiedEmail;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @JsonIgnore
    public LeadInformation getLeadId() {
        return LeadId;
    }

    public void setLeadId(LeadInformation leadId) {
        LeadId = leadId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Date getSystemDate() {
        return systemDate;
    }

    public void setSystemDate(Date systemDate) {
        this.systemDate = systemDate;
    }

    public Date getLocalDate() {
        return localDate;
    }

    public void setLocalDate(Date locxalDate) {
        this.localDate = locxalDate;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getLatPh() {
        return latPh;
    }

    public void setLatPh(String latPh) {
        this.latPh = latPh;
    }

    public String getLangPn() {
        return langPn;
    }

    public void setLangPn(String langPn) {
        this.langPn = langPn;
    }

    public Date getSystemDatePh() {
        return systemDatePh;
    }

    public void setSystemDatePh(Date systemDatePh) {
        this.systemDatePh = systemDatePh;
    }

    public Date getLocalDatePh() {
        return localDatePh;
    }

    public void setLocalDatePh(Date localDatePh) {
        this.localDatePh = localDatePh;
    }

    public String getLatEmail() {
        return latEmail;
    }

    public void setLatEmail(String latEmail) {
        this.latEmail = latEmail;
    }

    public String getLangEmail() {
        return langEmail;
    }

    public void setLangEmail(String langEmail) {
        this.langEmail = langEmail;
    }

    public Date getSystemDateEmail() {
        return systemDateEmail;
    }

    public void setSystemDateEmail(Date systemDateEmail) {
        this.systemDateEmail = systemDateEmail;
    }

    public Date getLocalDateEmail() {
        return localDateEmail;
    }

    public void setLocalDateEmail(Date localDateEmail) {
        this.localDateEmail = localDateEmail;
    }

    public String getLat2() {
        return lat2;
    }

    public void setLat2(String lat2) {
        this.lat2 = lat2;
    }

    public String getLang2() {
        return lang2;
    }

    public void setLang2(String lang2) {
        this.lang2 = lang2;
    }

    public Date getSystemDate2() {
        return systemDate2;
    }

    public void setSystemDate2(Date systemDate2) {
        this.systemDate2 = systemDate2;
    }

    public Date getLocalDate2() {
        return localDate2;
    }

    public void setLocalDate2(Date localDate2) {
        this.localDate2 = localDate2;
    }

    public String getIpAddressPh() {
        return ipAddressPh;
    }

    public void setIpAddressPh(String ipAddressPh) {
        this.ipAddressPh = ipAddressPh;
    }

    public String getIpAddressEmail() {
        return ipAddressEmail;
    }

    public void setIpAddressEmail(String ipAddressEmail) {
        this.ipAddressEmail = ipAddressEmail;
    }

    public String getIpAddress2() {
        return ipAddress2;
    }

    public void setIpAddress2(String ipAddress2) {
        this.ipAddress2 = ipAddress2;
    }

    public boolean isVerifiedPh() {
        return verifiedPh;
    }

    public void setVerifiedPh(boolean verifiedPh) {
        this.verifiedPh = verifiedPh;
    }

    public boolean isVerifiedEmail() {
        return verifiedEmail;
    }

    public void setVerifiedEmail(boolean verifiedEmail) {
        this.verifiedEmail = verifiedEmail;
    }
}
