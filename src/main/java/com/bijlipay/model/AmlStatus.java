package com.bijlipay.model;


import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;

@Entity
@Table(name = "aml_status")
public class AmlStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aml_id", nullable = false)
    private Long amlId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", nullable = false, unique = true)
    @JsonIgnore
    private LeadInformation lead;

    @Column(name = "status", length = 40, nullable = true)
    private String status;

    @Column(name = "document",  nullable = true)
    private String document;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Long getAmlId() {
        return amlId;
    }

    public void setAmlId(Long amlId) {
        this.amlId = amlId;
    }

    public LeadInformation getLead() {
        return lead;
    }

    public void setLead(LeadInformation lead) {
        this.lead = lead;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}



