package com.bijlipay.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mars_onboarding_information")
public class MarsOnboardingInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mars_onboarding_id", nullable = false)
    private int id;

    @Column(name = "is_active", columnDefinition = "tinyint(1) default 1")
    private boolean isActive;

    @Column(name = "onboarding_status", columnDefinition = "int(11) default 0")
    private Integer onboardingStatus;

    @JsonIgnoreProperties(value = "createdBymi")
    @ManyToOne(optional = true)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;

    @JsonIgnoreProperties(value = "updatedBymi")
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

    @JsonBackReference(value = "leadInformationmi")
    @JsonIgnoreProperties("leadInformationmi")
    @ManyToOne(optional = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "lead_id", referencedColumnName = "lead_id")
    private LeadInformation leadInformation;

    @Column(name = "application_number", nullable = true)
    private String applicationNumber;

    @Column(name = "merchant_reference_code", columnDefinition = "int(11) default 0")
    private Integer merchantRefCode;

    @Column(name = "sales_information", nullable = true,columnDefinition = "TEXT")
    private String salesInformation;


    @Column(name = "company_information", nullable = true,columnDefinition = "TEXT")
    private String companyInformation;

    @Column(name = "business_information", nullable = true,columnDefinition = "TEXT")
    private String businessInformation;

    @Column(name = "partner_information", nullable = true,columnDefinition = "TEXT")
    private String partnerInformation;

    @Column(name = "payment_details", nullable = true,columnDefinition = "TEXT")
    private String paymentDetails;

    @Column(name = "bank_information", nullable = true,columnDefinition = "TEXT")
    private String bankInformation;

    @Column(name = "additional_info", nullable = true,columnDefinition = "TEXT")
    private String additionalInfo;

    @Column(name = "custom_incentive_rates", nullable = true,columnDefinition = "TEXT")
    private String customIncentiveRates;

    @Column(name = "mcc", nullable = true)
    private String mcc;

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    @Column(name = "mdr_plan", nullable = true,columnDefinition = "TEXT")
    private String mdrPlan;

    @Column(name = "sharing_discount_fee", nullable = true,columnDefinition = "TEXT")
    private String SharingDiscountFee;

    @Column(name = "hold_payment", nullable = true)
    private String holdPayment;

    @Column(name = "lead_dataentry_remarks", nullable = true)
    private String leadDataEntryRemarks;

    @Column(name = "qr_application_number", nullable = true)
    private String qrApplicationNumber;

    @Column(name = "merchant_qr_reference_code", columnDefinition = "int(11) default 0")
    private Integer merchantQrRefCode;


    @Column(name = "edd_amount",columnDefinition="Decimal(10,2) default '0.00'")
    private double eddAmount;


    public String getHoldPayment() {
        return holdPayment;
    }

    public void setHoldPayment(String holdPayment) {
        this.holdPayment = holdPayment;
    }

    public String getLeadDataEntryRemarks() {
        return leadDataEntryRemarks;
    }

    public void setLeadDataEntryRemarks(String leadDataEntryRemarks) {
        this.leadDataEntryRemarks = leadDataEntryRemarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Integer getOnboardingStatus() {
        return onboardingStatus;
    }

    public void setOnboardingStatus(Integer onboardingStatus) {
        this.onboardingStatus = onboardingStatus;
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

    public LeadInformation getLeadInformation() {
        return leadInformation;
    }

    public void setLeadInformation(LeadInformation leadInformation) {
        this.leadInformation = leadInformation;
    }

    public String getSalesInformation() {
        return salesInformation;
    }

    public void setSalesInformation(String salesInformation) {
        this.salesInformation = salesInformation;
    }

    public String getCompanyInformation() {
        return companyInformation;
    }

    public void setCompanyInformation(String companyInformation) {
        this.companyInformation = companyInformation;
    }

    public String getBusinessInformation() {
        return businessInformation;
    }

    public void setBusinessInformation(String businessInformation) {
        this.businessInformation = businessInformation;
    }

    public String getPartnerInformation() {
        return partnerInformation;
    }

    public void setPartnerInformation(String partnerInformation) {
        this.partnerInformation = partnerInformation;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getBankInformation() {
        return bankInformation;
    }

    public void setBankInformation(String bankInformation) {
        this.bankInformation = bankInformation;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Integer getMerchantRefCode() {
        return merchantRefCode;
    }

    public void setMerchantRefCode(Integer merchantRefCode) {
        this.merchantRefCode = merchantRefCode;
    }

    public String getMdrPlan() {
        return mdrPlan;
    }

    public void setMdrPlan(String mdrPlan) {
        this.mdrPlan = mdrPlan;
    }

    public String getSharingDiscountFee() {
        return SharingDiscountFee;
    }

    public void setSharingDiscountFee(String sharingDiscountFee) {
        this.SharingDiscountFee = sharingDiscountFee;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getCustomIncentiveRates() {
        return customIncentiveRates;
    }

    public String getQrApplicationNumber() {
        return qrApplicationNumber;
    }

    public void setQrApplicationNumber(String qrApplicationNumber) {
        this.qrApplicationNumber = qrApplicationNumber;
    }

    public Integer getMerchantQrRefCode() {
        return merchantQrRefCode;
    }

    public void setMerchantQrRefCode(Integer merchantQrRefCode) {
        this.merchantQrRefCode = merchantQrRefCode;
    }

    public void setCustomIncentiveRates(String customIncentiveRates) {
        this.customIncentiveRates = customIncentiveRates;
    }

    public double getEddAmount() {
        return eddAmount;
    }

    public void setEddAmount(double eddAmount) {
        this.eddAmount = eddAmount;
    }

    @Override
    public String toString() {
        return "MarsOnboardingInformation{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", onboardingStatus=" + onboardingStatus +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", leadInformation=" + leadInformation +
                ", applicationNumber='" + applicationNumber + '\'' +
                ", merchantRefCode=" + merchantRefCode +
                ", salesInformation='" + salesInformation + '\'' +
                ", companyInformation='" + companyInformation + '\'' +
                ", businessInformation='" + businessInformation + '\'' +
                ", partnerInformation='" + partnerInformation + '\'' +
                ", paymentDetails='" + paymentDetails + '\'' +
                ", bankInformation='" + bankInformation + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", customIncentiveRates='" + customIncentiveRates + '\'' +
                ", mcc='" + mcc + '\'' +
                ", mdrPlan='" + mdrPlan + '\'' +
                ", SharingDiscountFee='" + SharingDiscountFee + '\'' +
                ", holdPayment='" + holdPayment + '\'' +
                ", leadDataEntryRemarks='" + leadDataEntryRemarks + '\'' +
                ", qrApplicationNumber='" + qrApplicationNumber + '\'' +
                ", merchantQrRefCode=" + merchantQrRefCode +
                ", eddAmount=" + eddAmount +
                '}';
    }
}
