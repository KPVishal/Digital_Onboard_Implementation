package com.bijlipay.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bank_subvention")
public class BankSubvention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "lead_source", referencedColumnName = "lead_source_id")
    private LeadSource leadSource;

    @ManyToOne(optional = true)
    @JoinColumn(name = "device", referencedColumnName = "device_id")
    private Device device;

    @Column(name = "mcc")
    private String mcc;

    @Column(name = "device_count")
    private Integer deviceCount;

    @Column(name = "account_number")
    private long accountNumber;

    @Column(name = "re_enter_account_number")
    private long reEnterAccountNumber;
    ;
    @JoinColumn(name = "mars_mdr_charges",referencedColumnName = "id",nullable = true)
    @ManyToOne(optional = true)
    private MarsMdrCharges mdrPlan;

    @JoinColumn(name = "mars_rental_plan",referencedColumnName = "id",nullable = true)
    @ManyToOne(optional = true)
    private MarsRentalPlans rentalPlan;


    @Column(name = "bank_domestic_debit_upto_2000")
    private String bankDomesticDebitUpTo2000;

    @Column(name = "bank_domestic_debit_above_2000")
    private String bankDomesticDebitAbove2000;

    @Column(name = "bank_standard_or_classic")
    private String bankStandardOrClassic;

    @Column(name = "bank_premium_or_platinum")
    private String bankPremiumOrPlatinum;

    @Column(name = "bank_super_premium_or_signature")
    private String bankSuperPremiumOrSignature;

    @Column(name = "bank_commercial_or_corporate")
    private String bankCommercialOrCorporate;

    @Column(name = "bank_international_debit_card")
    private String bankInternationalDebitCard;

    @Column(name = "bank_international_credit_card")
    private String bankInternationalCreditCard;

    @Column(name = "bank_onus")
    private String bankOnus;

    @Column(name = "bank_diners")
    private String bankDiners;

    @Column(name = "bank_cash_at_pos")
    private String bankCashAtPos;

    @Column(name = "bank_master_pass")
    private String bankMasterPass;

    @Column(name = "merchant_domestic_debit_upto_2000")
    private String merchantDomesticDebitUpTo2000;

    @Column(name = "merchant_domestic_debit_above_2000")
    private String merchantDomesticDebitAbove2000;

    @Column(name = "merchant_standard_or_classic")
    private String merchantStandardOrClassic;

    @Column(name = "merchant_premium_or_platinum")
    private String merchantPremiumOrPlatinum;

    @Column(name = "merchant_super_premium_or_signature")
    private String merchantSuperPremiumOrSignature;

    @Column(name = "merchant_commercial_or_corporate")
    private String merchantCommercialOrCorporate;

    @Column(name = "merchant_international_debit_card")
    private String merchantInternationalDebitCard;

    @Column(name = "merchant_international_credit_card")
    private String merchantInternationalCreditCard;

    @Column(name = "merchant_onus")
    private String merchantOnus;

    @Column(name = "merchant_diners")
    private String merchantDiners;

    @Column(name = "merchant_cash_at_pos")
    private String merchantCashAtPos;

    @Column(name = "merchant_master_pass")
    private String merchantMasterPass;
    @Column(name = "merchant_setup_fee")
    private String merchantSetupFee;
    @Column(name = "merchant_recurring_fee")
    private String merchantRecurringFee;
    @Column(name = "bank_setup_fee")
    private String bankSetupFee;
    @Column(name = "bank_recurring_fee")
    private String bankRecurringFee;
    @Column(name = "status")
    private int status;

    @Column(name = "file_name")
    private String fileName;
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "remarks")
    private String remarks;
    @Column(name = "mime_type")
    private String mineType;
    @Column(name = "total_device_count")
    private Integer totalDeviceCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getReEnterAccountNumber() {
        return reEnterAccountNumber;
    }

    public void setReEnterAccountNumber(long reEnterAccountNumber) {
        this.reEnterAccountNumber = reEnterAccountNumber;
    }

    public MarsMdrCharges getMdrPlan() {
        return mdrPlan;
    }

    public void setMdrPlan(MarsMdrCharges mdrPlan) {
        this.mdrPlan = mdrPlan;
    }

    public MarsRentalPlans getRentalPlan() {
        return rentalPlan;
    }

    public void setRentalPlan(MarsRentalPlans rentalPlan) {
        this.rentalPlan = rentalPlan;
    }

    public String getBankDomesticDebitUpTo2000() {
        return bankDomesticDebitUpTo2000;
    }

    public void setBankDomesticDebitUpTo2000(String bankDomesticDebitUpTo2000) {
        this.bankDomesticDebitUpTo2000 = bankDomesticDebitUpTo2000;
    }

    public String getBankDomesticDebitAbove2000() {
        return bankDomesticDebitAbove2000;
    }

    public void setBankDomesticDebitAbove2000(String bankDomesticDebitAbove2000) {
        this.bankDomesticDebitAbove2000 = bankDomesticDebitAbove2000;
    }

    public String getBankStandardOrClassic() {
        return bankStandardOrClassic;
    }

    public void setBankStandardOrClassic(String bankStandardOrClassic) {
        this.bankStandardOrClassic = bankStandardOrClassic;
    }

    public String getBankPremiumOrPlatinum() {
        return bankPremiumOrPlatinum;
    }

    public void setBankPremiumOrPlatinum(String bankPremiumOrPlatinum) {
        this.bankPremiumOrPlatinum = bankPremiumOrPlatinum;
    }

    public String getBankSuperPremiumOrSignature() {
        return bankSuperPremiumOrSignature;
    }

    public void setBankSuperPremiumOrSignature(String bankSuperPremiumOrSignature) {
        this.bankSuperPremiumOrSignature = bankSuperPremiumOrSignature;
    }

    public String getBankCommercialOrCorporate() {
        return bankCommercialOrCorporate;
    }

    public void setBankCommercialOrCorporate(String bankCommercialOrCorporate) {
        this.bankCommercialOrCorporate = bankCommercialOrCorporate;
    }

    public String getBankInternationalDebitCard() {
        return bankInternationalDebitCard;
    }

    public void setBankInternationalDebitCard(String bankInternationalDebitCard) {
        this.bankInternationalDebitCard = bankInternationalDebitCard;
    }

    public String getBankInternationalCreditCard() {
        return bankInternationalCreditCard;
    }

    public void setBankInternationalCreditCard(String bankInternationalCreditCard) {
        this.bankInternationalCreditCard = bankInternationalCreditCard;
    }

    public String getBankOnus() {
        return bankOnus;
    }

    public void setBankOnus(String bankOnus) {
        this.bankOnus = bankOnus;
    }

    public String getBankDiners() {
        return bankDiners;
    }

    public void setBankDiners(String bankDiners) {
        this.bankDiners = bankDiners;
    }

    public String getBankCashAtPos() {
        return bankCashAtPos;
    }

    public void setBankCashAtPos(String bankCashAtPos) {
        this.bankCashAtPos = bankCashAtPos;
    }

    public String getBankMasterPass() {
        return bankMasterPass;
    }

    public void setBankMasterPass(String bankMasterPass) {
        this.bankMasterPass = bankMasterPass;
    }

    public String getMerchantDomesticDebitUpTo2000() {
        return merchantDomesticDebitUpTo2000;
    }

    public void setMerchantDomesticDebitUpTo2000(String merchantDomesticDebitUpTo2000) {
        this.merchantDomesticDebitUpTo2000 = merchantDomesticDebitUpTo2000;
    }

    public String getMerchantDomesticDebitAbove2000() {
        return merchantDomesticDebitAbove2000;
    }

    public void setMerchantDomesticDebitAbove2000(String merchantDomesticDebitAbove2000) {
        this.merchantDomesticDebitAbove2000 = merchantDomesticDebitAbove2000;
    }

    public String getMerchantStandardOrClassic() {
        return merchantStandardOrClassic;
    }

    public void setMerchantStandardOrClassic(String merchantStandardOrClassic) {
        this.merchantStandardOrClassic = merchantStandardOrClassic;
    }

    public String getMerchantPremiumOrPlatinum() {
        return merchantPremiumOrPlatinum;
    }

    public void setMerchantPremiumOrPlatinum(String merchantPremiumOrPlatinum) {
        this.merchantPremiumOrPlatinum = merchantPremiumOrPlatinum;
    }

    public String getMerchantSuperPremiumOrSignature() {
        return merchantSuperPremiumOrSignature;
    }

    public void setMerchantSuperPremiumOrSignature(String merchantSuperPremiumOrSignature) {
        this.merchantSuperPremiumOrSignature = merchantSuperPremiumOrSignature;
    }

    public String getMerchantCommercialOrCorporate() {
        return merchantCommercialOrCorporate;
    }

    public void setMerchantCommercialOrCorporate(String merchantCommercialOrCorporate) {
        this.merchantCommercialOrCorporate = merchantCommercialOrCorporate;
    }

    public String getMerchantInternationalDebitCard() {
        return merchantInternationalDebitCard;
    }

    public void setMerchantInternationalDebitCard(String merchantInternationalDebitCard) {
        this.merchantInternationalDebitCard = merchantInternationalDebitCard;
    }

    public String getMerchantInternationalCreditCard() {
        return merchantInternationalCreditCard;
    }

    public void setMerchantInternationalCreditCard(String merchantInternationalCreditCard) {
        this.merchantInternationalCreditCard = merchantInternationalCreditCard;
    }

    public String getMerchantOnus() {
        return merchantOnus;
    }

    public void setMerchantOnus(String merchantOnus) {
        this.merchantOnus = merchantOnus;
    }

    public String getMerchantDiners() {
        return merchantDiners;
    }

    public void setMerchantDiners(String merchantDiners) {
        this.merchantDiners = merchantDiners;
    }

    public String getMerchantCashAtPos() {
        return merchantCashAtPos;
    }

    public void setMerchantCashAtPos(String merchantCashAtPos) {
        this.merchantCashAtPos = merchantCashAtPos;
    }

    public String getMerchantMasterPass() {
        return merchantMasterPass;
    }

    public void setMerchantMasterPass(String merchantMasterPass) {
        this.merchantMasterPass = merchantMasterPass;
    }

    public String getMerchantSetupFee() {
        return merchantSetupFee;
    }

    public void setMerchantSetupFee(String merchantSetupFee) {
        this.merchantSetupFee = merchantSetupFee;
    }

    public String getMerchantRecurringFee() {
        return merchantRecurringFee;
    }

    public void setMerchantRecurringFee(String merchantRecurringFee) {
        this.merchantRecurringFee = merchantRecurringFee;
    }

    public String getBankSetupFee() {
        return bankSetupFee;
    }

    public void setBankSetupFee(String bankSetupFee) {
        this.bankSetupFee = bankSetupFee;
    }

    public String getBankRecurringFee() {
        return bankRecurringFee;
    }

    public void setBankRecurringFee(String bankRecurringFee) {
        this.bankRecurringFee = bankRecurringFee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMineType() {
        return mineType;
    }

    public void setMineType(String mineType) {
        this.mineType = mineType;
    }

    @Override
    public String toString() {
        return "BankSubvention{" +
                "id=" + id +
                ", leadSource='" + leadSource + '\'' +
                ", device='" + device + '\'' +
                ", mcc='" + mcc + '\'' +
                ", deviceCount=" + deviceCount +
                ", accountNumber=" + accountNumber +
                ", reEnterAccountNumber=" + reEnterAccountNumber +
                ", mdrPlan=" + mdrPlan +
                ", rentalPlan=" + rentalPlan +
                ", bankDomesticDebitUpTo2000='" + bankDomesticDebitUpTo2000 + '\'' +
                ", bankDomesticDebitAbove2000='" + bankDomesticDebitAbove2000 + '\'' +
                ", bankStandardOrClassic='" + bankStandardOrClassic + '\'' +
                ", bankPremiumOrPlatinum='" + bankPremiumOrPlatinum + '\'' +
                ", bankSuperPremiumOrSignature='" + bankSuperPremiumOrSignature + '\'' +
                ", bankCommercialOrCorporate='" + bankCommercialOrCorporate + '\'' +
                ", bankInternationalDebitCard='" + bankInternationalDebitCard + '\'' +
                ", bankInternationalCreditCard='" + bankInternationalCreditCard + '\'' +
                ", bankOnus='" + bankOnus + '\'' +
                ", bankDiners='" + bankDiners + '\'' +
                ", bankCashAtPos='" + bankCashAtPos + '\'' +
                ", bankMasterPass='" + bankMasterPass + '\'' +
                ", merchantDomesticDebitUpTo2000='" + merchantDomesticDebitUpTo2000 + '\'' +
                ", merchantDomesticDebitAbove2000='" + merchantDomesticDebitAbove2000 + '\'' +
                ", merchantStandardOrClassic='" + merchantStandardOrClassic + '\'' +
                ", merchantPremiumOrPlatinum='" + merchantPremiumOrPlatinum + '\'' +
                ", merchantSuperPremiumOrSignature='" + merchantSuperPremiumOrSignature + '\'' +
                ", merchantCommercialOrCorporate='" + merchantCommercialOrCorporate + '\'' +
                ", merchantInternationalDebitCard='" + merchantInternationalDebitCard + '\'' +
                ", merchantInternationalCreditCard='" + merchantInternationalCreditCard + '\'' +
                ", merchantOnus='" + merchantOnus + '\'' +
                ", merchantDiners='" + merchantDiners + '\'' +
                ", merchantCashAtPos='" + merchantCashAtPos + '\'' +
                ", merchantMasterPass='" + merchantMasterPass + '\'' +
                ", merchantSetupFee='" + merchantSetupFee + '\'' +
                ", merchantRecurringFee='" + merchantRecurringFee + '\'' +
                ", bankSetupFee='" + bankSetupFee + '\'' +
                ", bankRecurringFee='" + bankRecurringFee + '\'' +
                ", status=" + status +
                '}';
    }
}
