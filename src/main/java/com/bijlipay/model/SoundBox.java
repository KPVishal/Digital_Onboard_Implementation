package com.bijlipay.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sound_box")
public class SoundBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "sound_box_no",nullable = false)
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "lead_id",referencedColumnName = "lead_id")
    private LeadInformation leadInformation;

    @OneToMany(mappedBy = "soundBox", cascade = CascadeType.ALL)
    private List<VpaQr> vpas = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<VpaQr> getVpas() {
        return vpas;
    }

    public void setVpas(List<VpaQr> vpas) {
        this.vpas = vpas;
    }

    public LeadInformation getLeadInformation() {
        return leadInformation;
    }

    public void setLeadInformation(LeadInformation leadInformation) {
        this.leadInformation = leadInformation;
    }
}
