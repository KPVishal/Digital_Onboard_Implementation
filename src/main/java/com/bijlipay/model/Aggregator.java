package com.bijlipay.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "aggregator")
public class Aggregator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aggregator_id", nullable = false)
    private long id;

    @Column(name = "aggregator", nullable = false, unique = true)
    private String aggregator;

    @ManyToOne(optional = true)
    @JoinColumn(name = "onboarding_type", referencedColumnName = "onboard_types_id")
    private OnboardingTypes onboardingTypes;

    @ManyToOne(optional = true)
    @JoinColumn(name = "settlement_type", referencedColumnName = "settlement_types_id")
    private SettlementTypes settlementTypes;

    @ManyToOne(optional = true)
    @JoinColumn(name = "lead_source", referencedColumnName = "lead_source_id")
    private LeadSource leadSource;

    @ManyToOne(optional = true)
    @JoinColumn(name = "device", referencedColumnName = "device_id")
    private Device device;

    @Column(name = "lead_provision", columnDefinition = "tinyint(1) default 0")
    private boolean leadProvision;

    @Column(name = "logo_name", nullable = true)
    private String logoName;

    @Column(name = "is_active", columnDefinition = "tinyint(1) default 1")
    private boolean isActive;

    @ManyToOne(optional = true)
    @JoinColumn(name = "kyc", referencedColumnName = "kyc_id", nullable = true)
    private Kyc kyc;

    @ManyToOne(optional = true)
    @JoinColumn(name = "agreement", referencedColumnName = "agreement_id", nullable = true)
    private Agreement agreement;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Transient
    private List<AggregatorDocumentMapping> aggregatorDocumentMappings;

    @Transient
    private String logoUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAggregator() {
        return aggregator;
    }

    public void setAggregator(String aggregator) {
        this.aggregator = aggregator;
    }

    public OnboardingTypes getOnboardingTypes() {
        return onboardingTypes;
    }

    public void setOnboardingTypes(OnboardingTypes onboardingTypes) {
        this.onboardingTypes = onboardingTypes;
    }

    public SettlementTypes getSettlementTypes() {
        return settlementTypes;
    }

    public void setSettlementTypes(SettlementTypes settlementTypes) {
        this.settlementTypes = settlementTypes;
    }

    public LeadSource getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(LeadSource leadSource) {
        this.leadSource = leadSource;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public boolean isLeadProvision() {
        return leadProvision;
    }

    public void setLeadProvision(boolean leadProvision) {
        this.leadProvision = leadProvision;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Kyc getKyc() {
        return kyc;
    }

    public void setKyc(Kyc kyc) {
        this.kyc = kyc;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
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

    public List<AggregatorDocumentMapping> getAggregatorDocumentMappings() {
        return aggregatorDocumentMappings;
    }

    public void setAggregatorDocumentMappings(List<AggregatorDocumentMapping> aggregatorDocumentMappings) {
        this.aggregatorDocumentMappings = aggregatorDocumentMappings;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        return "Aggregator{" +
                "id=" + id +
                ", aggregator='" + aggregator + '\'' +
                ", onboardingTypes=" + onboardingTypes +
                ", settlementTypes=" + settlementTypes +
                ", leadSource=" + leadSource +
                ", device=" + device +
                ", leadProvision=" + leadProvision +
                ", logoName='" + logoName + '\'' +
                ", isActive=" + isActive +
                ", kyc=" + kyc +
                ", agreement=" + agreement +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", aggregatorDocumentMappings=" + aggregatorDocumentMappings +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }
}
