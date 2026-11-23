package com.bijlipay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "qr_vpa_data")
public class VpaQr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "qr_vpa_id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "lead_id",referencedColumnName = "lead_id")
    private LeadInformation leadInformation;

    @Column(name = "vpa",nullable = true)
    private String vpa;
    @Column(name="is_active",columnDefinition = "tinyint(1) default 0")
    private boolean is_Active;
    @Column(name="is_soundBox",columnDefinition = "tinyint(1) default 0")
    private boolean isSoundBox;

    @Column(name = "soundBox_serialNo",nullable = true)
    private String soundBoxSerialNo;

    //Not Used soundBox
    @ManyToOne
    @JoinColumn(name = "sound_box_id",referencedColumnName = "id")
    private SoundBox soundBox;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeadInformation getLeadInformation() {
        return leadInformation;
    }

    public void setLeadInformation(LeadInformation leadInformation) {
        this.leadInformation = leadInformation;
    }

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }

    public boolean isIs_Active() {
        return is_Active;
    }

    public void setIs_Active(boolean is_Active) {
        this.is_Active = is_Active;
    }

    public SoundBox getSoundBox() {
        return soundBox;
    }

    public void setSoundBox(SoundBox soundBox) {
        this.soundBox = soundBox;
    }

    public String getSoundBoxSerialNo() {
        return soundBoxSerialNo;
    }

    public void setSoundBoxSerialNo(String soundBoxSerialNo) {
        this.soundBoxSerialNo = soundBoxSerialNo;
    }

    public boolean isSoundBox() {
        return isSoundBox;
    }

    public void setSoundBox(boolean soundBox) {
        isSoundBox = soundBox;
    }

    @Override
    public String toString() {
        return "VpaQr{" +
                "id=" + id +
                ", leadInformation=" + leadInformation +
                ", vpa='" + vpa + '\'' +
                ", is_Active=" + is_Active +
                ", soundBoxSerialNo='" + soundBoxSerialNo + '\'' +
                ", soundBox=" + soundBox +
                '}';
    }
}
