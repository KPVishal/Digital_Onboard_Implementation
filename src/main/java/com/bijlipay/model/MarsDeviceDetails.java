package com.bijlipay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mars_device_details")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class MarsDeviceDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mars_device_id", nullable = false)
    private long id;
    @Column(name = "tid", nullable = true)
    private String tid;
    @Column(name = "mid", nullable = true)
    private String mid;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
//    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", referencedColumnName = "lead_id")
    private LeadInformation leadInformation;
    @ManyToOne(optional = true)
    @JoinColumn(name = "assigned_to", referencedColumnName = "user_id")
    private User assignedTo;

    @Column(name = "email", nullable = true)
    private String email;
    @Column(name = "is_active", columnDefinition = "tinyint(1) default 1")
    private boolean isActive;
    @Column(name = "device_status", columnDefinition = "int(11) default 1")
    private Integer deviceStatus; //1-mars tid,mid generated, 2- assigned to implementation officer 3- scan and pick,
    @Column(name = "device_address", nullable = true)
    private String deviceAddress;
    @Column(name = "latitude", nullable = true)
    private Double latitude;
    @Column(name = "longitude", nullable = true)
    private Double longitude;
    @Column(name = "pincode", nullable = true)
    private long pincode;
    @Column(name = "city", nullable = true)
    private String city;
    @Column(name = "state_name", nullable = true)
    private String state;
    @Column(name = "device_status_date", nullable = true)
    private Date deviceStatusDate;
    @ManyToOne(optional = true)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;
    @ManyToOne(optional = true)
    @JoinColumn(name = "updated_by", referencedColumnName = "user_id")
    private User updatedBy;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "application_number", nullable = true)
    private String applicationNumber;

    @Column(name = "impl_state_name", nullable = true)
    private String implStateName;
    @Column(name = "impl_district_name", nullable = true)
    private String implDistrictName;
    @Column(name = "impl_sub_district_name", nullable = true)
    private String implSubDistrictName;
    @Column(name = "impl_village_name", nullable = true)
    private String implVillageName;
    @Column(name = "impl_tier", nullable = true)
    private String implTier;
    @Column(name = "impl_lat_lng", nullable = true)
    private String implLatLng;

    @Column(name = "sim", nullable = true)
    private String sim;

    @Column(name = "schedule_date", nullable = true)
    private Date scheduleDate;

    @Column(name = "re_schedule_date", nullable = true)
    private Date reScheduleDate;
    @Column(name = "reschdule_remarks", nullable = true)
    private String rescheduleRemarks;

    @Column(name = "reschdule_reason", nullable = true)
    private String rescheduleReason;
    @Column(name = "terminal_sno", nullable = true)
    private String terminalSNo;
    @Column(name = "is_document_collected", columnDefinition = "tinyint(1) default 0")
    private boolean isDocumentCollected;

    @Column(name = "merchant_name", nullable = true)
    private String merchantName;

    @Column(name = "impl_device_complete_date", nullable = true)
    private Date implDeviceCompleteDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "impl_schedule_complete_date", nullable = true)
    private Date implScheduleCompleteDate;

    @Column(name = "implementation_complete_date", nullable = true)
    private Date implementationCompleteDate;
    @Column(name = "sqr_vpa", nullable = true)
    private String sqrVpa;

    @Column(name = "sqr_count", columnDefinition = "tinyint(1) default 0")
    private int sqrCount;

    @Column(name = "tid_key", nullable = true)
    private String tidKey;

    @Column(name = "qr_status", columnDefinition = "int(11) default 0")
    private Integer qrStatus; // 1.scan and pick

    @Column(name = "qr_status_date", nullable = true)
    private Date qrStatusDate;

    @Column(name = "vpa", nullable = true)
    private String vpa;

    @Column(name = "soundbox_sno", nullable = true)
    private String soundboxSNo;

    @Column(name = "impl_pincode_name", nullable = true)

    private String implPincodeName;

    @Column(name = "impl_city_name", nullable = true)

    private String implCityName;

    @Column(name = "impl_location_name", nullable = true)

    private String implLocationName;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public LeadInformation getLeadInformation() {
        return leadInformation;
    }

    public void setLeadInformation(LeadInformation leadInformation) {
        this.leadInformation = leadInformation;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public long getPincode() {
        return pincode;
    }

    public void setPincode(long pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDeviceStatusDate() {
        return deviceStatusDate;
    }

    public void setDeviceStatusDate(Date deviceStatusDate) {
        this.deviceStatusDate = deviceStatusDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
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

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Date getReScheduleDate() {
        return reScheduleDate;
    }

    public void setReScheduleDate(Date reScheduleDate) {
        this.reScheduleDate = reScheduleDate;
    }

    public String getRescheduleRemarks() {
        return rescheduleRemarks;
    }

    public void setRescheduleRemarks(String rescheduleRemarks) {
        this.rescheduleRemarks = rescheduleRemarks;
    }

    public String getRescheduleReason() {
        return rescheduleReason;
    }

    public void setRescheduleReason(String rescheduleReason) {
        this.rescheduleReason = rescheduleReason;
    }

    public String getTerminalSNo() {
        return terminalSNo;
    }

    public void setTerminalSNo(String terminalSNo) {
        this.terminalSNo = terminalSNo;
    }

    public boolean isDocumentCollected() {
        return isDocumentCollected;
    }

    public void setDocumentCollected(boolean documentCollected) {
        isDocumentCollected = documentCollected;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Date getImplDeviceCompleteDate() {
        return implDeviceCompleteDate;
    }

    public void setImplDeviceCompleteDate(Date implDeviceCompleteDate) {
        this.implDeviceCompleteDate = implDeviceCompleteDate;
    }

    public Date getImplScheduleCompleteDate() {
        return implScheduleCompleteDate;
    }

    public void setImplScheduleCompleteDate(Date implScheduleCompleteDate) {
        this.implScheduleCompleteDate = implScheduleCompleteDate;
    }

    public Date getImplementationCompleteDate() {
        return implementationCompleteDate;
    }

    public void setImplementationCompleteDate(Date implementationCompleteDate) {
        this.implementationCompleteDate = implementationCompleteDate;
    }

    public String getSqrVpa() {
        return sqrVpa;
    }

    public int getSqrCount() {
        return sqrCount;
    }

    public void setSqrCount(int sqrCount) {
        this.sqrCount = sqrCount;
    }

    public void setSqrVpa(String sqrVpa) {
        this.sqrVpa = sqrVpa;
    }

    public String getTidKey() {
        return tidKey;
    }

    public void setTidKey(String tidKey) {
        this.tidKey = tidKey;
    }


    public Integer getQrStatus() {
        return qrStatus;
    }

    public void setQrStatus(Integer qrStatus) {
        this.qrStatus = qrStatus;
    }

    public Date getQrStatusDate() {
        return qrStatusDate;
    }

    public void setQrStatusDate(Date qrStatusDate) {
        this.qrStatusDate = qrStatusDate;
    }

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }

    public String getSoundboxSNo() {
        return soundboxSNo;
    }

    public void setSoundboxSNo(String soundboxSNo) {
        this.soundboxSNo = soundboxSNo;
    }

    public String getImplPincodeName() {
        return implPincodeName;
    }

    public void setImplPincodeName(String implPincodeName) {
        this.implPincodeName = implPincodeName;
    }

    public String getImplCityName() {
        return implCityName;
    }

    public void setImplCityName(String implCityName) {
        this.implCityName = implCityName;
    }

    public String getImplLocationName() {
        return implLocationName;
    }

    public void setImplLocationName(String implLocationName) {
        this.implLocationName = implLocationName;
    }
}
