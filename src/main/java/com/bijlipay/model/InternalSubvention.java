package com.bijlipay.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "internal_subvention")
public class InternalSubvention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "pos")
    private boolean pos;
    @Column(name = "sticker")
    private boolean sticker;
    @Column(name = "soundBox")
    private boolean soundBox;

    @Column(name = "mcc_changed",columnDefinition = "tinyint(1) default 0")
    private boolean mccChanged;

    @Column(name = "pos_mdr")
    private boolean posMdr;
    @Column(name = "qr_mdr")
    private boolean qrMdr;
    @Column(name = "soundBox_mdr")
    private boolean soundBoxMdr;
    @Column(name = "pos_rental")
    private boolean posRental;
    @Column(name = "qr_rental")
    private boolean qrRental;
    @Column(name = "soundBox_rental")
    private boolean soundBoxRental;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "nh_rejected",columnDefinition = "tinyint(1) default 0")
    private boolean nhRejected;

    @ManyToOne(optional = true)
    @JoinColumn(name = "authorizer", referencedColumnName = "user_id")
    private User Authorizer;
    @Column(name = "nh_pos_rental_status",columnDefinition ="int(11) default 0")
    private int nhPosRentalStatus;
    @Column(name = "nh_pos_mdr_status",columnDefinition ="int(11) default 0")
    private int nhPosMDRStatus;
    @Column(name = "nh_sticker_rental_status",columnDefinition ="int(11) default 0")
    private int nhStickerRentalStatus;
    @Column(name = "nh_soundbox_rental_status",columnDefinition ="int(11) default 0")
    private int nhSoundBoxRentalStatus;
    @Column(name = "nh_soundbox_mdr_status",columnDefinition ="int(11) default 0")
    private int nhSoundBoxMDRStatus;
    @Column(name = "nh_sticker_mdr_status",columnDefinition ="int(11) default 0")
    private int nhStickerMDRStatus;
    @Column(name = "nh_pos_rental_remark", nullable = true,columnDefinition= "VARCHAR(50)")
    private String nhPosRentalRemark;
    @Column(name = "nh_pos_mdr_remark", nullable = true,columnDefinition= "VARCHAR(50)")
    private String nhPosMDRRemark;
    @Column(name = "nh_sticker_rental_remark", nullable = true,columnDefinition= "VARCHAR(50)")
    private String nhStickerRentalRemark;
    @Column(name = "nh_soundbox_rental_remark", nullable = true,columnDefinition= "VARCHAR(50)")
    private String nhSoundBoxRentalRemark;
    @Column(name = "nh_soundbox_mdr_remark", nullable = true,columnDefinition= "VARCHAR(50)")
    private String nhSoundBoxMDRRemark;
    @Column(name = "nh_sticker_mdr_remark", nullable = true,columnDefinition= "VARCHAR(50)")
    private String nhStickerMDRRemark;
    @Column(name = "pos_requested_domestic_debit_up_to_2000", columnDefinition = "JSON")
    private String posRequestedDomesticDebitUpTo2000;

    @Column(name = "pos_approved_domestic_debit_up_to_2000", columnDefinition = "JSON")
    private String posApprovedDomesticDebitUpTo2000;

    @Column(name = "pos_requested_domestic_debit_above_2000", columnDefinition = "JSON")
    private String posRequestedDomesticDebitAbove2000;

    @Column(name = "pos_approved_domestic_debit_above_2000", columnDefinition = "JSON")
    private String posApprovedDomesticDebitAbove2000;

    @Column(name = "pos_requested_standard_or_classic", columnDefinition = "JSON")
    private String posRequestedStandardOrClassic;

    @Column(name = "pos_approved_standard_or_classic", columnDefinition = "JSON")
    private String posApprovedStandardOrClassic;

    @Column(name = "pos_requested_premium_or_platinum", columnDefinition = "JSON")
    private String posRequestedPremiumOrPlatinum;

    @Column(name = "pos_approved_premium_or_platinum", columnDefinition = "JSON")
    private String posApprovedPremiumOrPlatinum;

    @Column(name = "pos_requested_international_debit_card", columnDefinition = "JSON")
    private String posRequestedInternationalDebitCard;

    @Column(name = "pos_approved_international_debit_card", columnDefinition = "JSON")
    private String posApprovedInternationalDebitCard;

    @Column(name = "pos_requested_commercial_or_corporate", columnDefinition = "JSON")
    private String posRequestedCommercialOrCorporate;

    @Column(name = "pos_approved_commercial_or_corporate", columnDefinition = "JSON")
    private String posApprovedCommercialOrCorporate;

    @Column(name = "pos_requested_super_premium_or_signature", columnDefinition = "JSON")
    private String posRequestedSuperPremiumOrSignature;

    @Column(name = "pos_approved_super_premium_or_signature", columnDefinition = "JSON")
    private String posApprovedSuperPremiumOrSignature;

    @Column(name = "pos_requested_international_credit_card", columnDefinition = "JSON")
    private String posRequestedInternationalCreditCard;

    @Column(name = "pos_approved_international_credit_card", columnDefinition = "JSON")
    private String posApprovedInternationalCreditCard;

    @Column(name = "pos_requested_onus", columnDefinition = "JSON")
    private String posRequestedOnus;

    @Column(name = "pos_approved_onus", columnDefinition = "JSON")
    private String posApprovedOnus;

    @Column(name = "pos_requested_diners", columnDefinition = "JSON")
    private String posRequestedDiners;

    @Column(name = "pos_approved_diners", columnDefinition = "JSON")
    private String posApprovedDiners;

    @Column(name = "pos_requested_master_pass", columnDefinition = "JSON")
    private String posRequestedMasterPass;

    @Column(name = "pos_approved_master_pass", columnDefinition = "JSON")
    private String posApprovedMasterPass;

    @Column(name = "pos_requested_cash_at_pos", columnDefinition = "JSON")
    private String posRequestedCashAtPos;

    @Column(name = "pos_approved_cash_at_pos", columnDefinition = "JSON")
    private String posApprovedCashAtPos;

    @Column(name = "pos_requested_amex_domestic", columnDefinition = "JSON")
    private String posRequestedAmexDomestic;

    @Column(name = "pos_approved_amex_domestic", columnDefinition = "JSON")
    private String posApprovedAmexDomestic;

    @Column(name = "pos_requested_amex_international", columnDefinition = "JSON")
    private String posRequestedAmexInternational;

    @Column(name = "pos_approved_amex_international", columnDefinition = "JSON")
    private String posApprovedAmexInternational;
    @Column(name = "pos_requested_upiCreditAbove2000", columnDefinition = "JSON")
    private String posRequestedUpiCreditAbove2000;

    @Column(name = "pos_approved_upiCreditAbove2000", columnDefinition = "JSON")
    private String posApprovedUpiCreditAbove2000;
    @Column(name = "pos_requested_upiCreditUpto2000", columnDefinition = "JSON")
    private String posRequestedUpiCreditUpto2000;

    @Column(name = "pos_approved_upiCreditUpto2000", columnDefinition = "JSON")
    private String posApprovedUpiCreditUpto2000;

    @Column(name = "sticker_requested_domestic_debit_up_to_2000", columnDefinition = "JSON")
    private String stickerRequestedDomesticDebitUpTo2000;

    @Column(name = "sticker_approved_domestic_debit_up_to_2000", columnDefinition = "JSON")
    private String stickerApprovedDomesticDebitUpTo2000;

    @Column(name = "sticker_requested_domestic_debit_above_2000", columnDefinition = "JSON")
    private String stickerRequestedDomesticDebitAbove2000;

    @Column(name = "sticker_approved_domestic_debit_above_2000", columnDefinition = "JSON")
    private String stickerApprovedDomesticDebitAbove2000;

    @Column(name = "sticker_requested_standard_or_classic", columnDefinition = "JSON")
    private String stickerRequestedStandardOrClassic;

    @Column(name = "sticker_approved_standard_or_classic", columnDefinition = "JSON")
    private String stickerApprovedStandardOrClassic;

    @Column(name = "sticker_requested_premium_or_platinum", columnDefinition = "JSON")
    private String stickerRequestedPremiumOrPlatinum;

    @Column(name = "sticker_approved_premium_or_platinum", columnDefinition = "JSON")
    private String stickerApprovedPremiumOrPlatinum;

    @Column(name = "sticker_requested_international_debit_card", columnDefinition = "JSON")
    private String stickerRequestedInternationalDebitCard;

    @Column(name = "sticker_approved_international_debit_card", columnDefinition = "JSON")
    private String stickerApprovedInternationalDebitCard;

    @Column(name = "sticker_requested_commercial_or_corporate", columnDefinition = "JSON")
    private String stickerRequestedCommercialOrCorporate;

    @Column(name = "sticker_approved_commercial_or_corporate", columnDefinition = "JSON")
    private String stickerApprovedCommercialOrCorporate;

    @Column(name = "sticker_requested_super_premium_or_signature", columnDefinition = "JSON")
    private String stickerRequestedSuperPremiumOrSignature;

    @Column(name = "sticker_approved_super_premium_or_signature", columnDefinition = "JSON")
    private String stickerApprovedSuperPremiumOrSignature;

    @Column(name = "sticker_requested_international_credit_card", columnDefinition = "JSON")
    private String stickerRequestedInternationalCreditCard;

    @Column(name = "sticker_approved_international_credit_card", columnDefinition = "JSON")
    private String stickerApprovedInternationalCreditCard;

    @Column(name = "sticker_requested_onus", columnDefinition = "JSON")
    private String stickerRequestedOnus;

    @Column(name = "sticker_approved_onus", columnDefinition = "JSON")
    private String stickerApprovedOnus;

    @Column(name = "sticker_requested_diners", columnDefinition = "JSON")
    private String stickerRequestedDiners;

    @Column(name = "sticker_approved_diners", columnDefinition = "JSON")
    private String stickerApprovedDiners;

    @Column(name = "sticker_requested_master_pass", columnDefinition = "JSON")
    private String stickerRequestedMasterPass;

    @Column(name = "sticker_approved_master_pass", columnDefinition = "JSON")
    private String stickerApprovedMasterPass;

    @Column(name = "sticker_requested_cash_at_pos", columnDefinition = "JSON")
    private String stickerRequestedCashAtPos;

    @Column(name = "sticker_approved_cash_at_pos", columnDefinition = "JSON")
    private String stickerApprovedCashAtPos;

    @Column(name = "sticker_requested_amex_domestic", columnDefinition = "JSON")
    private String stickerRequestedAmexDomestic;

    @Column(name = "sticker_approved_amex_domestic", columnDefinition = "JSON")
    private String stickerApprovedAmexDomestic;

    @Column(name = "sticker_requested_amex_international", columnDefinition = "JSON")
    private String stickerRequestedAmexInternational;

    @Column(name = "sticker_approved_amex_international", columnDefinition = "JSON")
    private String stickerApprovedAmexInternational;
    @Column(name = "sticker_requested_upiCreditAbove2000", columnDefinition = "JSON")
    private String stickerRequestedUpiCreditAbove2000;

    @Column(name = "sticker_approved_upiCreditAbove2000", columnDefinition = "JSON")
    private String stickerApprovedUpiCreditAbove2000;
    @Column(name = "sticker_requested_upiCreditUpto2000", columnDefinition = "JSON")
    private String stickerRequestedUpiCreditUpto2000;

    @Column(name = "sticker_approved_upiCreditUpto2000", columnDefinition = "JSON")
    private String stickerApprovedUpiCreditUpto2000;
    @Column(name = "sound_box_requested_domestic_debit_up_to_2000", columnDefinition = "JSON")
    private String soundBoxRequestedDomesticDebitUpTo2000;

    @Column(name = "sound_box_approved_domestic_debit_up_to_2000", columnDefinition = "JSON")
    private String soundBoxApprovedDomesticDebitUpTo2000;

    @Column(name = "sound_box_requested_domestic_debit_above_2000", columnDefinition = "JSON")
    private String soundBoxRequestedDomesticDebitAbove2000;

    @Column(name = "sound_box_approved_domestic_debit_above_2000", columnDefinition = "JSON")
    private String soundBoxApprovedDomesticDebitAbove2000;

    @Column(name = "sound_box_requested_standard_or_classic", columnDefinition = "JSON")
    private String soundBoxRequestedStandardOrClassic;

    @Column(name = "sound_box_approved_standard_or_classic", columnDefinition = "JSON")
    private String soundBoxApprovedStandardOrClassic;

    @Column(name = "sound_box_requested_premium_or_platinum", columnDefinition = "JSON")
    private String soundBoxRequestedPremiumOrPlatinum;

    @Column(name = "sound_box_approved_premium_or_platinum", columnDefinition = "JSON")
    private String soundBoxApprovedPremiumOrPlatinum;

    @Column(name = "sound_box_requested_international_debit_card", columnDefinition = "JSON")
    private String soundBoxRequestedInternationalDebitCard;

    @Column(name = "sound_box_approved_international_debit_card", columnDefinition = "JSON")
    private String soundBoxApprovedInternationalDebitCard;

    @Column(name = "sound_box_requested_commercial_or_corporate", columnDefinition = "JSON")
    private String soundBoxRequestedCommercialOrCorporate;

    @Column(name = "sound_box_approved_commercial_or_corporate", columnDefinition = "JSON")
    private String soundBoxApprovedCommercialOrCorporate;

    @Column(name = "sound_box_requested_super_premium_or_signature", columnDefinition = "JSON")
    private String soundBoxRequestedSuperPremiumOrSignature;

    @Column(name = "sound_box_approved_super_premium_or_signature", columnDefinition = "JSON")
    private String soundBoxApprovedSuperPremiumOrSignature;

    @Column(name = "sound_box_requested_international_credit_card", columnDefinition = "JSON")
    private String soundBoxRequestedInternationalCreditCard;

    @Column(name = "sound_box_approved_international_credit_card", columnDefinition = "JSON")
    private String soundBoxApprovedInternationalCreditCard;

    @Column(name = "sound_box_requested_onus", columnDefinition = "JSON")
    private String soundBoxRequestedOnus;

    @Column(name = "sound_box_approved_onus", columnDefinition = "JSON")
    private String soundBoxApprovedOnus;

    @Column(name = "sound_box_requested_diners", columnDefinition = "JSON")
    private String soundBoxRequestedDiners;

    @Column(name = "sound_box_approved_diners", columnDefinition = "JSON")
    private String soundBoxApprovedDiners;

    @Column(name = "sound_box_requested_master_pass", columnDefinition = "JSON")
    private String soundBoxRequestedMasterPass;

    @Column(name = "sound_box_approved_master_pass", columnDefinition = "JSON")
    private String soundBoxApprovedMasterPass;

    @Column(name = "sound_box_requested_cash_at_pos", columnDefinition = "JSON")
    private String soundBoxRequestedCashAtPos;

    @Column(name = "sound_box_approved_cash_at_pos", columnDefinition = "JSON")
    private String soundBoxApprovedCashAtPos;

    @Column(name = "sound_box_requested_amex_domestic", columnDefinition = "JSON")
    private String soundBoxRequestedAmexDomestic;

    @Column(name = "sound_box_approved_amex_domestic", columnDefinition = "JSON")
    private String soundBoxApprovedAmexDomestic;

    @Column(name = "sound_box_requested_amex_international", columnDefinition = "JSON")
    private String soundBoxRequestedAmexInternational;

    @Column(name = "sound_box_approved_amex_international", columnDefinition = "JSON")
    private String soundBoxApprovedAmexInternational;
    @Column(name = "pos_approved_setup_fee")
    private String posApprovedSetupFee;
    @Column(name = "pos_requested_setup_fee")
    private String posRequestedSetupFee;
    @Column(name = "pos_approved_recurring_fee")
    private String posApprovedRecurringFee;
    @Column(name = "pos_requested_recurring_fee")
    private String posRequestedRecurringFee;
    @Column(name = "sticker_approved_recurring_fee")
    private String stickerApprovedRecurringFee;
    @Column(name = "sticker_requested_recurring_fee")
    private String stickerRequestedRecurringFee;

    @Column(name = "sticker_approved_setup_fee")
    private String stickerApprovedSetupFee;
    @Column(name = "sticker_requested_setup_fee")
    private String stickerRequestedSetupFee;
    @Column(name = "sound_box_approved_setup_fee")
    private String soundBoxApprovedSetupFee;
    @Column(name = "sound_box_requested_setup_fee")
    private String soundBoxRequestedSetupFee;
    @Column(name = "sound_box_approved_recurring_fee")
    private String soundBoxApprovedRecurringFee;
    @Column(name = "sound_box_requested_recurring_fee")
    private String soundBoxRequestedRecurringFee;
    @Column(name = "soundBox_requested_upiCreditAbove2000", columnDefinition = "JSON")
    private String soundBoxRequestedUpiCreditAbove2000;

    @Column(name = "soundBox_approved_upiCreditAbove2000", columnDefinition = "JSON")
    private String soundBoxApprovedUpiCreditAbove2000;
    @Column(name = "soundBox_requested_upiCreditUpto2000", columnDefinition = "JSON")
    private String soundBoxRequestedUpiCreditUpto2000;

    @Column(name = "soundBox_approved_upiCreditUpto2000", columnDefinition = "JSON")
    private String soundBoxApprovedUpiCreditUpto2000;


    @Column(name = "Lead_id")
    private long leadId;

    @Column(name = "status")
    private int status;
    @Column(name = "remarks", columnDefinition = "JSON")
    private String remarks;

    @Transient
    private LeadInformation leadInformation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isPos() {
        return pos;
    }

    public void setPos(boolean pos) {
        this.pos = pos;
    }

    public boolean isSticker() {
        return sticker;
    }

    public void setSticker(boolean sticker) {
        this.sticker = sticker;
    }

    public boolean isSoundBox() {
        return soundBox;
    }

    public void setSoundBox(boolean soundBox) {
        this.soundBox = soundBox;
    }

    public String getPosRequestedDomesticDebitUpTo2000() {
        return posRequestedDomesticDebitUpTo2000;
    }

    public void setPosRequestedDomesticDebitUpTo2000(String posRequestedDomesticDebitUpTo2000) {
        this.posRequestedDomesticDebitUpTo2000 = posRequestedDomesticDebitUpTo2000;
    }

    public String getPosApprovedDomesticDebitUpTo2000() {
        return posApprovedDomesticDebitUpTo2000;
    }

    public void setPosApprovedDomesticDebitUpTo2000(String posApprovedDomesticDebitUpTo2000) {
        this.posApprovedDomesticDebitUpTo2000 = posApprovedDomesticDebitUpTo2000;
    }

    public String getPosRequestedDomesticDebitAbove2000() {
        return posRequestedDomesticDebitAbove2000;
    }

    public void setPosRequestedDomesticDebitAbove2000(String posRequestedDomesticDebitAbove2000) {
        this.posRequestedDomesticDebitAbove2000 = posRequestedDomesticDebitAbove2000;
    }

    public String getPosApprovedDomesticDebitAbove2000() {
        return posApprovedDomesticDebitAbove2000;
    }

    public void setPosApprovedDomesticDebitAbove2000(String posApprovedDomesticDebitAbove2000) {
        this.posApprovedDomesticDebitAbove2000 = posApprovedDomesticDebitAbove2000;
    }

    public String getPosRequestedStandardOrClassic() {
        return posRequestedStandardOrClassic;
    }

    public void setPosRequestedStandardOrClassic(String posRequestedStandardOrClassic) {
        this.posRequestedStandardOrClassic = posRequestedStandardOrClassic;
    }

    public String getPosApprovedStandardOrClassic() {
        return posApprovedStandardOrClassic;
    }

    public void setPosApprovedStandardOrClassic(String posApprovedStandardOrClassic) {
        this.posApprovedStandardOrClassic = posApprovedStandardOrClassic;
    }

    public String getPosRequestedPremiumOrPlatinum() {
        return posRequestedPremiumOrPlatinum;
    }

    public void setPosRequestedPremiumOrPlatinum(String posRequestedPremiumOrPlatinum) {
        this.posRequestedPremiumOrPlatinum = posRequestedPremiumOrPlatinum;
    }

    public String getPosApprovedPremiumOrPlatinum() {
        return posApprovedPremiumOrPlatinum;
    }

    public void setPosApprovedPremiumOrPlatinum(String posApprovedPremiumOrPlatinum) {
        this.posApprovedPremiumOrPlatinum = posApprovedPremiumOrPlatinum;
    }

    public String getPosRequestedInternationalDebitCard() {
        return posRequestedInternationalDebitCard;
    }

    public void setPosRequestedInternationalDebitCard(String posRequestedInternationalDebitCard) {
        this.posRequestedInternationalDebitCard = posRequestedInternationalDebitCard;
    }

    public String getPosApprovedInternationalDebitCard() {
        return posApprovedInternationalDebitCard;
    }

    public void setPosApprovedInternationalDebitCard(String posApprovedInternationalDebitCard) {
        this.posApprovedInternationalDebitCard = posApprovedInternationalDebitCard;
    }

    public String getPosRequestedCommercialOrCorporate() {
        return posRequestedCommercialOrCorporate;
    }

    public void setPosRequestedCommercialOrCorporate(String posRequestedCommercialOrCorporate) {
        this.posRequestedCommercialOrCorporate = posRequestedCommercialOrCorporate;
    }

    public String getPosApprovedCommercialOrCorporate() {
        return posApprovedCommercialOrCorporate;
    }

    public void setPosApprovedCommercialOrCorporate(String posApprovedCommercialOrCorporate) {
        this.posApprovedCommercialOrCorporate = posApprovedCommercialOrCorporate;
    }

    public String getPosRequestedSuperPremiumOrSignature() {
        return posRequestedSuperPremiumOrSignature;
    }

    public void setPosRequestedSuperPremiumOrSignature(String posRequestedSuperPremiumOrSignature) {
        this.posRequestedSuperPremiumOrSignature = posRequestedSuperPremiumOrSignature;
    }

    public String getPosApprovedSuperPremiumOrSignature() {
        return posApprovedSuperPremiumOrSignature;
    }

    public void setPosApprovedSuperPremiumOrSignature(String posApprovedSuperPremiumOrSignature) {
        this.posApprovedSuperPremiumOrSignature = posApprovedSuperPremiumOrSignature;
    }

    public String getPosRequestedInternationalCreditCard() {
        return posRequestedInternationalCreditCard;
    }

    public void setPosRequestedInternationalCreditCard(String posRequestedInternationalCreditCard) {
        this.posRequestedInternationalCreditCard = posRequestedInternationalCreditCard;
    }

    public String getPosApprovedInternationalCreditCard() {
        return posApprovedInternationalCreditCard;
    }

    public void setPosApprovedInternationalCreditCard(String posApprovedInternationalCreditCard) {
        this.posApprovedInternationalCreditCard = posApprovedInternationalCreditCard;
    }

    public String getPosRequestedOnus() {
        return posRequestedOnus;
    }

    public void setPosRequestedOnus(String posRequestedOnus) {
        this.posRequestedOnus = posRequestedOnus;
    }

    public String getPosApprovedOnus() {
        return posApprovedOnus;
    }

    public void setPosApprovedOnus(String posApprovedOnus) {
        this.posApprovedOnus = posApprovedOnus;
    }

    public String getPosRequestedDiners() {
        return posRequestedDiners;
    }

    public void setPosRequestedDiners(String posRequestedDiners) {
        this.posRequestedDiners = posRequestedDiners;
    }

    public String getPosApprovedDiners() {
        return posApprovedDiners;
    }

    public void setPosApprovedDiners(String posApprovedDiners) {
        this.posApprovedDiners = posApprovedDiners;
    }

    public String getPosRequestedMasterPass() {
        return posRequestedMasterPass;
    }

    public void setPosRequestedMasterPass(String posRequestedMasterPass) {
        this.posRequestedMasterPass = posRequestedMasterPass;
    }

    public String getPosApprovedMasterPass() {
        return posApprovedMasterPass;
    }

    public void setPosApprovedMasterPass(String posApprovedMasterPass) {
        this.posApprovedMasterPass = posApprovedMasterPass;
    }

    public String getPosRequestedCashAtPos() {
        return posRequestedCashAtPos;
    }

    public void setPosRequestedCashAtPos(String posRequestedCashAtPos) {
        this.posRequestedCashAtPos = posRequestedCashAtPos;
    }

    public String getPosApprovedCashAtPos() {
        return posApprovedCashAtPos;
    }

    public void setPosApprovedCashAtPos(String posApprovedCashAtPos) {
        this.posApprovedCashAtPos = posApprovedCashAtPos;
    }

    public String getPosRequestedAmexDomestic() {
        return posRequestedAmexDomestic;
    }

    public void setPosRequestedAmexDomestic(String posRequestedAmexDomestic) {
        this.posRequestedAmexDomestic = posRequestedAmexDomestic;
    }

    public String getPosApprovedAmexDomestic() {
        return posApprovedAmexDomestic;
    }

    public void setPosApprovedAmexDomestic(String posApprovedAmexDomestic) {
        this.posApprovedAmexDomestic = posApprovedAmexDomestic;
    }

    public String getPosRequestedAmexInternational() {
        return posRequestedAmexInternational;
    }

    public void setPosRequestedAmexInternational(String posRequestedAmexInternational) {
        this.posRequestedAmexInternational = posRequestedAmexInternational;
    }

    public String getPosApprovedAmexInternational() {
        return posApprovedAmexInternational;
    }

    public void setPosApprovedAmexInternational(String posApprovedAmexInternational) {
        this.posApprovedAmexInternational = posApprovedAmexInternational;
    }

    public String getStickerRequestedDomesticDebitUpTo2000() {
        return stickerRequestedDomesticDebitUpTo2000;
    }

    public void setStickerRequestedDomesticDebitUpTo2000(String stickerRequestedDomesticDebitUpTo2000) {
        this.stickerRequestedDomesticDebitUpTo2000 = stickerRequestedDomesticDebitUpTo2000;
    }

    public String getStickerApprovedDomesticDebitUpTo2000() {
        return stickerApprovedDomesticDebitUpTo2000;
    }

    public void setStickerApprovedDomesticDebitUpTo2000(String stickerApprovedDomesticDebitUpTo2000) {
        this.stickerApprovedDomesticDebitUpTo2000 = stickerApprovedDomesticDebitUpTo2000;
    }

    public String getStickerRequestedDomesticDebitAbove2000() {
        return stickerRequestedDomesticDebitAbove2000;
    }

    public void setStickerRequestedDomesticDebitAbove2000(String stickerRequestedDomesticDebitAbove2000) {
        this.stickerRequestedDomesticDebitAbove2000 = stickerRequestedDomesticDebitAbove2000;
    }

    public String getStickerApprovedDomesticDebitAbove2000() {
        return stickerApprovedDomesticDebitAbove2000;
    }

    public void setStickerApprovedDomesticDebitAbove2000(String stickerApprovedDomesticDebitAbove2000) {
        this.stickerApprovedDomesticDebitAbove2000 = stickerApprovedDomesticDebitAbove2000;
    }

    public String getStickerRequestedStandardOrClassic() {
        return stickerRequestedStandardOrClassic;
    }

    public void setStickerRequestedStandardOrClassic(String stickerRequestedStandardOrClassic) {
        this.stickerRequestedStandardOrClassic = stickerRequestedStandardOrClassic;
    }

    public String getStickerApprovedStandardOrClassic() {
        return stickerApprovedStandardOrClassic;
    }

    public void setStickerApprovedStandardOrClassic(String stickerApprovedStandardOrClassic) {
        this.stickerApprovedStandardOrClassic = stickerApprovedStandardOrClassic;
    }

    public String getStickerRequestedPremiumOrPlatinum() {
        return stickerRequestedPremiumOrPlatinum;
    }

    public void setStickerRequestedPremiumOrPlatinum(String stickerRequestedPremiumOrPlatinum) {
        this.stickerRequestedPremiumOrPlatinum = stickerRequestedPremiumOrPlatinum;
    }

    public String getStickerApprovedPremiumOrPlatinum() {
        return stickerApprovedPremiumOrPlatinum;
    }

    public void setStickerApprovedPremiumOrPlatinum(String stickerApprovedPremiumOrPlatinum) {
        this.stickerApprovedPremiumOrPlatinum = stickerApprovedPremiumOrPlatinum;
    }

    public String getStickerRequestedInternationalDebitCard() {
        return stickerRequestedInternationalDebitCard;
    }

    public void setStickerRequestedInternationalDebitCard(String stickerRequestedInternationalDebitCard) {
        this.stickerRequestedInternationalDebitCard = stickerRequestedInternationalDebitCard;
    }

    public String getStickerApprovedInternationalDebitCard() {
        return stickerApprovedInternationalDebitCard;
    }

    public void setStickerApprovedInternationalDebitCard(String stickerApprovedInternationalDebitCard) {
        this.stickerApprovedInternationalDebitCard = stickerApprovedInternationalDebitCard;
    }

    public String getStickerRequestedCommercialOrCorporate() {
        return stickerRequestedCommercialOrCorporate;
    }

    public void setStickerRequestedCommercialOrCorporate(String stickerRequestedCommercialOrCorporate) {
        this.stickerRequestedCommercialOrCorporate = stickerRequestedCommercialOrCorporate;
    }

    public String getStickerApprovedCommercialOrCorporate() {
        return stickerApprovedCommercialOrCorporate;
    }

    public void setStickerApprovedCommercialOrCorporate(String stickerApprovedCommercialOrCorporate) {
        this.stickerApprovedCommercialOrCorporate = stickerApprovedCommercialOrCorporate;
    }

    public String getStickerRequestedSuperPremiumOrSignature() {
        return stickerRequestedSuperPremiumOrSignature;
    }

    public void setStickerRequestedSuperPremiumOrSignature(String stickerRequestedSuperPremiumOrSignature) {
        this.stickerRequestedSuperPremiumOrSignature = stickerRequestedSuperPremiumOrSignature;
    }

    public String getStickerApprovedSuperPremiumOrSignature() {
        return stickerApprovedSuperPremiumOrSignature;
    }

    public void setStickerApprovedSuperPremiumOrSignature(String stickerApprovedSuperPremiumOrSignature) {
        this.stickerApprovedSuperPremiumOrSignature = stickerApprovedSuperPremiumOrSignature;
    }

    public String getStickerRequestedInternationalCreditCard() {
        return stickerRequestedInternationalCreditCard;
    }

    public void setStickerRequestedInternationalCreditCard(String stickerRequestedInternationalCreditCard) {
        this.stickerRequestedInternationalCreditCard = stickerRequestedInternationalCreditCard;
    }

    public String getStickerApprovedInternationalCreditCard() {
        return stickerApprovedInternationalCreditCard;
    }

    public void setStickerApprovedInternationalCreditCard(String stickerApprovedInternationalCreditCard) {
        this.stickerApprovedInternationalCreditCard = stickerApprovedInternationalCreditCard;
    }

    public String getStickerRequestedOnus() {
        return stickerRequestedOnus;
    }

    public void setStickerRequestedOnus(String stickerRequestedOnus) {
        this.stickerRequestedOnus = stickerRequestedOnus;
    }

    public String getStickerApprovedOnus() {
        return stickerApprovedOnus;
    }

    public void setStickerApprovedOnus(String stickerApprovedOnus) {
        this.stickerApprovedOnus = stickerApprovedOnus;
    }

    public String getStickerRequestedDiners() {
        return stickerRequestedDiners;
    }

    public void setStickerRequestedDiners(String stickerRequestedDiners) {
        this.stickerRequestedDiners = stickerRequestedDiners;
    }

    public String getStickerApprovedDiners() {
        return stickerApprovedDiners;
    }

    public void setStickerApprovedDiners(String stickerApprovedDiners) {
        this.stickerApprovedDiners = stickerApprovedDiners;
    }

    public String getStickerRequestedMasterPass() {
        return stickerRequestedMasterPass;
    }

    public void setStickerRequestedMasterPass(String stickerRequestedMasterPass) {
        this.stickerRequestedMasterPass = stickerRequestedMasterPass;
    }

    public String getStickerApprovedMasterPass() {
        return stickerApprovedMasterPass;
    }

    public void setStickerApprovedMasterPass(String stickerApprovedMasterPass) {
        this.stickerApprovedMasterPass = stickerApprovedMasterPass;
    }

    public String getStickerRequestedCashAtPos() {
        return stickerRequestedCashAtPos;
    }

    public void setStickerRequestedCashAtPos(String stickerRequestedCashAtPos) {
        this.stickerRequestedCashAtPos = stickerRequestedCashAtPos;
    }

    public String getStickerApprovedCashAtPos() {
        return stickerApprovedCashAtPos;
    }

    public void setStickerApprovedCashAtPos(String stickerApprovedCashAtPos) {
        this.stickerApprovedCashAtPos = stickerApprovedCashAtPos;
    }

    public String getStickerRequestedAmexDomestic() {
        return stickerRequestedAmexDomestic;
    }

    public void setStickerRequestedAmexDomestic(String stickerRequestedAmexDomestic) {
        this.stickerRequestedAmexDomestic = stickerRequestedAmexDomestic;
    }

    public String getStickerApprovedAmexDomestic() {
        return stickerApprovedAmexDomestic;
    }

    public void setStickerApprovedAmexDomestic(String stickerApprovedAmexDomestic) {
        this.stickerApprovedAmexDomestic = stickerApprovedAmexDomestic;
    }

    public String getStickerRequestedAmexInternational() {
        return stickerRequestedAmexInternational;
    }

    public void setStickerRequestedAmexInternational(String stickerRequestedAmexInternational) {
        this.stickerRequestedAmexInternational = stickerRequestedAmexInternational;
    }

    public String getStickerApprovedAmexInternational() {
        return stickerApprovedAmexInternational;
    }

    public void setStickerApprovedAmexInternational(String stickerApprovedAmexInternational) {
        this.stickerApprovedAmexInternational = stickerApprovedAmexInternational;
    }

    public String getSoundBoxRequestedDomesticDebitUpTo2000() {
        return soundBoxRequestedDomesticDebitUpTo2000;
    }

    public void setSoundBoxRequestedDomesticDebitUpTo2000(String soundBoxRequestedDomesticDebitUpTo2000) {
        this.soundBoxRequestedDomesticDebitUpTo2000 = soundBoxRequestedDomesticDebitUpTo2000;
    }

    public String getSoundBoxApprovedDomesticDebitUpTo2000() {
        return soundBoxApprovedDomesticDebitUpTo2000;
    }

    public void setSoundBoxApprovedDomesticDebitUpTo2000(String soundBoxApprovedDomesticDebitUpTo2000) {
        this.soundBoxApprovedDomesticDebitUpTo2000 = soundBoxApprovedDomesticDebitUpTo2000;
    }

    public String getSoundBoxRequestedDomesticDebitAbove2000() {
        return soundBoxRequestedDomesticDebitAbove2000;
    }

    public void setSoundBoxRequestedDomesticDebitAbove2000(String soundBoxRequestedDomesticDebitAbove2000) {
        this.soundBoxRequestedDomesticDebitAbove2000 = soundBoxRequestedDomesticDebitAbove2000;
    }

    public String getSoundBoxApprovedDomesticDebitAbove2000() {
        return soundBoxApprovedDomesticDebitAbove2000;
    }

    public void setSoundBoxApprovedDomesticDebitAbove2000(String soundBoxApprovedDomesticDebitAbove2000) {
        this.soundBoxApprovedDomesticDebitAbove2000 = soundBoxApprovedDomesticDebitAbove2000;
    }

    public String getSoundBoxRequestedStandardOrClassic() {
        return soundBoxRequestedStandardOrClassic;
    }

    public void setSoundBoxRequestedStandardOrClassic(String soundBoxRequestedStandardOrClassic) {
        this.soundBoxRequestedStandardOrClassic = soundBoxRequestedStandardOrClassic;
    }

    public String getSoundBoxApprovedStandardOrClassic() {
        return soundBoxApprovedStandardOrClassic;
    }

    public void setSoundBoxApprovedStandardOrClassic(String soundBoxApprovedStandardOrClassic) {
        this.soundBoxApprovedStandardOrClassic = soundBoxApprovedStandardOrClassic;
    }

    public String getSoundBoxRequestedPremiumOrPlatinum() {
        return soundBoxRequestedPremiumOrPlatinum;
    }

    public void setSoundBoxRequestedPremiumOrPlatinum(String soundBoxRequestedPremiumOrPlatinum) {
        this.soundBoxRequestedPremiumOrPlatinum = soundBoxRequestedPremiumOrPlatinum;
    }

    public String getSoundBoxApprovedPremiumOrPlatinum() {
        return soundBoxApprovedPremiumOrPlatinum;
    }

    public void setSoundBoxApprovedPremiumOrPlatinum(String soundBoxApprovedPremiumOrPlatinum) {
        this.soundBoxApprovedPremiumOrPlatinum = soundBoxApprovedPremiumOrPlatinum;
    }

    public String getSoundBoxRequestedInternationalDebitCard() {
        return soundBoxRequestedInternationalDebitCard;
    }

    public void setSoundBoxRequestedInternationalDebitCard(String soundBoxRequestedInternationalDebitCard) {
        this.soundBoxRequestedInternationalDebitCard = soundBoxRequestedInternationalDebitCard;
    }

    public String getSoundBoxApprovedInternationalDebitCard() {
        return soundBoxApprovedInternationalDebitCard;
    }

    public void setSoundBoxApprovedInternationalDebitCard(String soundBoxApprovedInternationalDebitCard) {
        this.soundBoxApprovedInternationalDebitCard = soundBoxApprovedInternationalDebitCard;
    }

    public String getSoundBoxRequestedCommercialOrCorporate() {
        return soundBoxRequestedCommercialOrCorporate;
    }

    public void setSoundBoxRequestedCommercialOrCorporate(String soundBoxRequestedCommercialOrCorporate) {
        this.soundBoxRequestedCommercialOrCorporate = soundBoxRequestedCommercialOrCorporate;
    }

    public String getSoundBoxApprovedCommercialOrCorporate() {
        return soundBoxApprovedCommercialOrCorporate;
    }

    public void setSoundBoxApprovedCommercialOrCorporate(String soundBoxApprovedCommercialOrCorporate) {
        this.soundBoxApprovedCommercialOrCorporate = soundBoxApprovedCommercialOrCorporate;
    }

    public String getSoundBoxRequestedSuperPremiumOrSignature() {
        return soundBoxRequestedSuperPremiumOrSignature;
    }

    public void setSoundBoxRequestedSuperPremiumOrSignature(String soundBoxRequestedSuperPremiumOrSignature) {
        this.soundBoxRequestedSuperPremiumOrSignature = soundBoxRequestedSuperPremiumOrSignature;
    }

    public String getSoundBoxApprovedSuperPremiumOrSignature() {
        return soundBoxApprovedSuperPremiumOrSignature;
    }

    public void setSoundBoxApprovedSuperPremiumOrSignature(String soundBoxApprovedSuperPremiumOrSignature) {
        this.soundBoxApprovedSuperPremiumOrSignature = soundBoxApprovedSuperPremiumOrSignature;
    }

    public String getSoundBoxRequestedInternationalCreditCard() {
        return soundBoxRequestedInternationalCreditCard;
    }

    public void setSoundBoxRequestedInternationalCreditCard(String soundBoxRequestedInternationalCreditCard) {
        this.soundBoxRequestedInternationalCreditCard = soundBoxRequestedInternationalCreditCard;
    }

    public String getSoundBoxApprovedInternationalCreditCard() {
        return soundBoxApprovedInternationalCreditCard;
    }

    public void setSoundBoxApprovedInternationalCreditCard(String soundBoxApprovedInternationalCreditCard) {
        this.soundBoxApprovedInternationalCreditCard = soundBoxApprovedInternationalCreditCard;
    }

    public String getSoundBoxRequestedOnus() {
        return soundBoxRequestedOnus;
    }

    public void setSoundBoxRequestedOnus(String soundBoxRequestedOnus) {
        this.soundBoxRequestedOnus = soundBoxRequestedOnus;
    }

    public String getSoundBoxApprovedOnus() {
        return soundBoxApprovedOnus;
    }

    public void setSoundBoxApprovedOnus(String soundBoxApprovedOnus) {
        this.soundBoxApprovedOnus = soundBoxApprovedOnus;
    }

    public String getSoundBoxRequestedDiners() {
        return soundBoxRequestedDiners;
    }

    public void setSoundBoxRequestedDiners(String soundBoxRequestedDiners) {
        this.soundBoxRequestedDiners = soundBoxRequestedDiners;
    }

    public String getSoundBoxApprovedDiners() {
        return soundBoxApprovedDiners;
    }

    public void setSoundBoxApprovedDiners(String soundBoxApprovedDiners) {
        this.soundBoxApprovedDiners = soundBoxApprovedDiners;
    }

    public String getSoundBoxRequestedMasterPass() {
        return soundBoxRequestedMasterPass;
    }

    public void setSoundBoxRequestedMasterPass(String soundBoxRequestedMasterPass) {
        this.soundBoxRequestedMasterPass = soundBoxRequestedMasterPass;
    }

    public String getSoundBoxApprovedMasterPass() {
        return soundBoxApprovedMasterPass;
    }

    public void setSoundBoxApprovedMasterPass(String soundBoxApprovedMasterPass) {
        this.soundBoxApprovedMasterPass = soundBoxApprovedMasterPass;
    }

    public String getSoundBoxRequestedCashAtPos() {
        return soundBoxRequestedCashAtPos;
    }

    public void setSoundBoxRequestedCashAtPos(String soundBoxRequestedCashAtPos) {
        this.soundBoxRequestedCashAtPos = soundBoxRequestedCashAtPos;
    }

    public String getSoundBoxApprovedCashAtPos() {
        return soundBoxApprovedCashAtPos;
    }

    public void setSoundBoxApprovedCashAtPos(String soundBoxApprovedCashAtPos) {
        this.soundBoxApprovedCashAtPos = soundBoxApprovedCashAtPos;
    }

    public String getSoundBoxRequestedAmexDomestic() {
        return soundBoxRequestedAmexDomestic;
    }

    public void setSoundBoxRequestedAmexDomestic(String soundBoxRequestedAmexDomestic) {
        this.soundBoxRequestedAmexDomestic = soundBoxRequestedAmexDomestic;
    }

    public String getSoundBoxApprovedAmexDomestic() {
        return soundBoxApprovedAmexDomestic;
    }

    public void setSoundBoxApprovedAmexDomestic(String soundBoxApprovedAmexDomestic) {
        this.soundBoxApprovedAmexDomestic = soundBoxApprovedAmexDomestic;
    }

    public String getSoundBoxRequestedAmexInternational() {
        return soundBoxRequestedAmexInternational;
    }

    public void setSoundBoxRequestedAmexInternational(String soundBoxRequestedAmexInternational) {
        this.soundBoxRequestedAmexInternational = soundBoxRequestedAmexInternational;
    }

    public String getSoundBoxApprovedAmexInternational() {
        return soundBoxApprovedAmexInternational;
    }

    public void setSoundBoxApprovedAmexInternational(String soundBoxApprovedAmexInternational) {
        this.soundBoxApprovedAmexInternational = soundBoxApprovedAmexInternational;
    }

    public long getLeadId() {
        return leadId;
    }

    public void setLeadId(long leadId) {
        this.leadId = leadId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPosApprovedSetupFee() {
        return posApprovedSetupFee;
    }

    public void setPosApprovedSetupFee(String posApprovedSetupFee) {
        this.posApprovedSetupFee = posApprovedSetupFee;
    }

    public String getPosRequestedSetupFee() {
        return posRequestedSetupFee;
    }

    public void setPosRequestedSetupFee(String posRequestedSetupFee) {
        this.posRequestedSetupFee = posRequestedSetupFee;
    }

    public String getPosApprovedRecurringFee() {
        return posApprovedRecurringFee;
    }

    public void setPosApprovedRecurringFee(String posApprovedRecurringFee) {
        this.posApprovedRecurringFee = posApprovedRecurringFee;
    }

    public String getPosRequestedRecurringFee() {
        return posRequestedRecurringFee;
    }

    public void setPosRequestedRecurringFee(String posRequestedRecurringFee) {
        this.posRequestedRecurringFee = posRequestedRecurringFee;
    }

    public String getStickerApprovedRecurringFee() {
        return stickerApprovedRecurringFee;
    }

    public void setStickerApprovedRecurringFee(String stickerApprovedRecurringFee) {
        this.stickerApprovedRecurringFee = stickerApprovedRecurringFee;
    }

    public String getStickerRequestedRecurringFee() {
        return stickerRequestedRecurringFee;
    }

    public void setStickerRequestedRecurringFee(String stickerRequestedRecurringFee) {
        this.stickerRequestedRecurringFee = stickerRequestedRecurringFee;
    }

    public String getStickerApprovedSetupFee() {
        return stickerApprovedSetupFee;
    }

    public void setStickerApprovedSetupFee(String stickerApprovedSetupFee) {
        this.stickerApprovedSetupFee = stickerApprovedSetupFee;
    }

    public String getStickerRequestedSetupFee() {
        return stickerRequestedSetupFee;
    }

    public void setStickerRequestedSetupFee(String stickerRequestedSetupFee) {
        this.stickerRequestedSetupFee = stickerRequestedSetupFee;
    }

    public String getSoundBoxApprovedSetupFee() {
        return soundBoxApprovedSetupFee;
    }

    public void setSoundBoxApprovedSetupFee(String soundBoxApprovedSetupFee) {
        this.soundBoxApprovedSetupFee = soundBoxApprovedSetupFee;
    }

    public String getSoundBoxRequestedSetupFee() {
        return soundBoxRequestedSetupFee;
    }

    public void setSoundBoxRequestedSetupFee(String soundBoxRequestedSetupFee) {
        this.soundBoxRequestedSetupFee = soundBoxRequestedSetupFee;
    }

    public String getSoundBoxApprovedRecurringFee() {
        return soundBoxApprovedRecurringFee;
    }

    public void setSoundBoxApprovedRecurringFee(String soundBoxApprovedRecurringFee) {
        this.soundBoxApprovedRecurringFee = soundBoxApprovedRecurringFee;
    }

    public String getSoundBoxRequestedRecurringFee() {
        return soundBoxRequestedRecurringFee;
    }

    public void setSoundBoxRequestedRecurringFee(String soundBoxRequestedRecurringFee) {
        this.soundBoxRequestedRecurringFee = soundBoxRequestedRecurringFee;
    }

    public LeadInformation getLeadInformation() {
        return leadInformation;
    }

    public void setLeadInformation(LeadInformation leadInformation) {
        this.leadInformation = leadInformation;
    }

    public boolean isPosMdr() {
        return posMdr;
    }

    public void setPosMdr(boolean posMdr) {
        this.posMdr = posMdr;
    }

    public boolean isQrMdr() {
        return qrMdr;
    }

    public void setQrMdr(boolean qrMdr) {
        this.qrMdr = qrMdr;
    }

    public boolean isSoundBoxMdr() {
        return soundBoxMdr;
    }

    public void setSoundBoxMdr(boolean soundBoxMdr) {
        this.soundBoxMdr = soundBoxMdr;
    }

    public boolean isPosRental() {
        return posRental;
    }

    public void setPosRental(boolean posRental) {
        this.posRental = posRental;
    }

    public boolean isQrRental() {
        return qrRental;
    }

    public void setQrRental(boolean qrRental) {
        this.qrRental = qrRental;
    }

    public boolean isSoundBoxRental() {
        return soundBoxRental;
    }

    public void setSoundBoxRental(boolean soundBoxRental) {
        this.soundBoxRental = soundBoxRental;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isMccChanged() {
        return mccChanged;
    }

    public void setMccChanged(boolean mccChanged) {
        this.mccChanged = mccChanged;
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

    public boolean isNhRejected() {
        return nhRejected;
    }

    public void setNhRejected(boolean nhRejected) {
        this.nhRejected = nhRejected;
    }

    public User getAuthorizer() {
        return Authorizer;
    }

    public void setAuthorizer(User authorizer) {
        Authorizer = authorizer;
    }

    public int getNhPosRentalStatus() {
        return nhPosRentalStatus;
    }

    public void setNhPosRentalStatus(int nhPosRentalStatus) {
        this.nhPosRentalStatus = nhPosRentalStatus;
    }

    public int getNhPosMDRStatus() {
        return nhPosMDRStatus;
    }

    public void setNhPosMDRStatus(int nhPosMDRStatus) {
        this.nhPosMDRStatus = nhPosMDRStatus;
    }

    public int getNhStickerRentalStatus() {
        return nhStickerRentalStatus;
    }

    public void setNhStickerRentalStatus(int nhStickerRentalStatus) {
        this.nhStickerRentalStatus = nhStickerRentalStatus;
    }

    public int getNhSoundBoxRentalStatus() {
        return nhSoundBoxRentalStatus;
    }

    public void setNhSoundBoxRentalStatus(int nhSoundBoxRentalStatus) {
        this.nhSoundBoxRentalStatus = nhSoundBoxRentalStatus;
    }

    public int getNhSoundBoxMDRStatus() {
        return nhSoundBoxMDRStatus;
    }

    public void setNhSoundBoxMDRStatus(int nhSoundBoxMDRStatus) {
        this.nhSoundBoxMDRStatus = nhSoundBoxMDRStatus;
    }

    public int getNhStickerMDRStatus() {
        return nhStickerMDRStatus;
    }

    public void setNhStickerMDRStatus(int nhStickerMDRStatus) {
        this.nhStickerMDRStatus = nhStickerMDRStatus;
    }

    public String getNhPosRentalRemark() {
        return nhPosRentalRemark;
    }

    public void setNhPosRentalRemark(String nhPosRentalRemark) {
        this.nhPosRentalRemark = nhPosRentalRemark;
    }

    public String getNhPosMDRRemark() {
        return nhPosMDRRemark;
    }

    public void setNhPosMDRRemark(String nhPosMDRRemark) {
        this.nhPosMDRRemark = nhPosMDRRemark;
    }

    public String getNhStickerRentalRemark() {
        return nhStickerRentalRemark;
    }

    public void setNhStickerRentalRemark(String nhStickerRentalRemark) {
        this.nhStickerRentalRemark = nhStickerRentalRemark;
    }

    public String getNhSoundBoxRentalRemark() {
        return nhSoundBoxRentalRemark;
    }

    public void setNhSoundBoxRentalRemark(String nhSoundBoxRentalRemark) {
        this.nhSoundBoxRentalRemark = nhSoundBoxRentalRemark;
    }

    public String getNhSoundBoxMDRRemark() {
        return nhSoundBoxMDRRemark;
    }

    public void setNhSoundBoxMDRRemark(String nhSoundBoxMDRRemark) {
        this.nhSoundBoxMDRRemark = nhSoundBoxMDRRemark;
    }

    public String getNhStickerMDRRemark() {
        return nhStickerMDRRemark;
    }

    public void setNhStickerMDRRemark(String nhStickerMDRRemark) {
        this.nhStickerMDRRemark = nhStickerMDRRemark;
    }

    public String getPosRequestedUpiCreditAbove2000() {
        return posRequestedUpiCreditAbove2000;
    }

    public void setPosRequestedUpiCreditAbove2000(String posRequestedUpiCreditAbove2000) {
        this.posRequestedUpiCreditAbove2000 = posRequestedUpiCreditAbove2000;
    }

    public String getPosApprovedUpiCreditAbove2000() {
        return posApprovedUpiCreditAbove2000;
    }

    public void setPosApprovedUpiCreditAbove2000(String posApprovedUpiCreditAbove2000) {
        this.posApprovedUpiCreditAbove2000 = posApprovedUpiCreditAbove2000;
    }

    public String getPosRequestedUpiCreditUpto2000() {
        return posRequestedUpiCreditUpto2000;
    }

    public void setPosRequestedUpiCreditUpto2000(String posRequestedUpiCreditUpto2000) {
        this.posRequestedUpiCreditUpto2000 = posRequestedUpiCreditUpto2000;
    }

    public String getPosApprovedUpiCreditUpto2000() {
        return posApprovedUpiCreditUpto2000;
    }

    public void setPosApprovedUpiCreditUpto2000(String posApprovedUpiCreditUpto2000) {
        this.posApprovedUpiCreditUpto2000 = posApprovedUpiCreditUpto2000;
    }

    public String getStickerRequestedUpiCreditAbove2000() {
        return stickerRequestedUpiCreditAbove2000;
    }

    public void setStickerRequestedUpiCreditAbove2000(String stickerRequestedUpiCreditAbove2000) {
        this.stickerRequestedUpiCreditAbove2000 = stickerRequestedUpiCreditAbove2000;
    }

    public String getStickerApprovedUpiCreditAbove2000() {
        return stickerApprovedUpiCreditAbove2000;
    }

    public void setStickerApprovedUpiCreditAbove2000(String stickerApprovedUpiCreditAbove2000) {
        this.stickerApprovedUpiCreditAbove2000 = stickerApprovedUpiCreditAbove2000;
    }

    public String getStickerRequestedUpiCreditUpto2000() {
        return stickerRequestedUpiCreditUpto2000;
    }

    public void setStickerRequestedUpiCreditUpto2000(String stickerRequestedUpiCreditUpto2000) {
        this.stickerRequestedUpiCreditUpto2000 = stickerRequestedUpiCreditUpto2000;
    }

    public String getStickerApprovedUpiCreditUpto2000() {
        return stickerApprovedUpiCreditUpto2000;
    }

    public void setStickerApprovedUpiCreditUpto2000(String stickerApprovedUpiCreditUpto2000) {
        this.stickerApprovedUpiCreditUpto2000 = stickerApprovedUpiCreditUpto2000;
    }

    public String getSoundBoxRequestedUpiCreditAbove2000() {
        return soundBoxRequestedUpiCreditAbove2000;
    }

    public void setSoundBoxRequestedUpiCreditAbove2000(String soundBoxRequestedUpiCreditAbove2000) {
        this.soundBoxRequestedUpiCreditAbove2000 = soundBoxRequestedUpiCreditAbove2000;
    }

    public String getSoundBoxApprovedUpiCreditAbove2000() {
        return soundBoxApprovedUpiCreditAbove2000;
    }

    public void setSoundBoxApprovedUpiCreditAbove2000(String soundBoxApprovedUpiCreditAbove2000) {
        this.soundBoxApprovedUpiCreditAbove2000 = soundBoxApprovedUpiCreditAbove2000;
    }

    public String getSoundBoxRequestedUpiCreditUpto2000() {
        return soundBoxRequestedUpiCreditUpto2000;
    }

    public void setSoundBoxRequestedUpiCreditUpto2000(String soundBoxRequestedUpiCreditUpto2000) {
        this.soundBoxRequestedUpiCreditUpto2000 = soundBoxRequestedUpiCreditUpto2000;
    }

    public String getSoundBoxApprovedUpiCreditUpto2000() {
        return soundBoxApprovedUpiCreditUpto2000;
    }

    public void setSoundBoxApprovedUpiCreditUpto2000(String soundBoxApprovedUpiCreditUpto2000) {
        this.soundBoxApprovedUpiCreditUpto2000 = soundBoxApprovedUpiCreditUpto2000;
    }
}
