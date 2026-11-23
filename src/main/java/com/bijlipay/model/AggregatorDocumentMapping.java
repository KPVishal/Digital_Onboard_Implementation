package com.bijlipay.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "aggregator_document_mapping")
public class AggregatorDocumentMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aggregator_document_id", nullable = false)
    private long id;

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "aggregator", referencedColumnName = "aggregator_id")
    private Aggregator aggregator;

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "document_type", referencedColumnName = "document_types_id")
    private DocumentTypes documentTypes;

    @ManyToOne(optional = true)
    @JoinColumn(name = "document", referencedColumnName = "mapping_id", nullable = true)
    private DocumentMapping documentMapping;

    @ManyToOne(optional = true)
    @JoinColumn(name = "merchant_type", referencedColumnName = "merchant_type_id", nullable = true)
    private MerchantType merchantType;

    @ManyToOne(optional = true)
    @JoinColumn(name = "merchant_document_type", referencedColumnName = "document_type_id", nullable = true)
    private MerchantDocumentType merchantDocumentType;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Aggregator getAggregator() {
        return aggregator;
    }

    public void setAggregator(Aggregator aggregator) {
        this.aggregator = aggregator;
    }

    public DocumentTypes getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(DocumentTypes documentTypes) {
        this.documentTypes = documentTypes;
    }

    public DocumentMapping getDocumentMapping() {
        return documentMapping;
    }

    public void setDocumentMapping(DocumentMapping documentMapping) {
        this.documentMapping = documentMapping;
    }

    public MerchantType getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(MerchantType merchantType) {
        this.merchantType = merchantType;
    }

    public MerchantDocumentType getMerchantDocumentType() {
        return merchantDocumentType;
    }

    public void setMerchantDocumentType(MerchantDocumentType merchantDocumentType) {
        this.merchantDocumentType = merchantDocumentType;
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
        return "AggregatorDocumentMapping{" +
                "id=" + id +
                ", aggregator=" + aggregator +
                ", documentTypes=" + documentTypes +
                ", documentMapping=" + documentMapping +
                ", merchantType=" + merchantType +
                ", merchantDocumentType=" + merchantDocumentType +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
