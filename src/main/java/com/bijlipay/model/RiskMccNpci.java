package com.bijlipay.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "risk_mcc_npci")
public class RiskMccNpci {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "mcc_mcc_id",referencedColumnName = "mcc_id")
    private MerchantCategoryCode mcc;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MerchantCategoryCode getMcc() {
        return mcc;
    }

    public void setMcc(MerchantCategoryCode mcc) {
        this.mcc = mcc;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "RiskMccNpci{" +
                "id=" + id +
                ", mcc=" + mcc +
                ", createdAt=" + createdAt +
                '}';
    }
}
