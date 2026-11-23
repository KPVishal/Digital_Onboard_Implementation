package com.bijlipay.model;

import javax.persistence.*;

public class SubventionMdr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "req_domestic_debit_upto_2000", columnDefinition = "JSON")
    private String reqDomesticDebitUpTo2000;
    @Column(name = "req_domestic_debit_above_2000", columnDefinition = "JSON")
    private String reqDomesticDebitAbove2000;
    @Column(name = "req_standard_or_classic", columnDefinition = "JSON")
    private String reqStandardOrClassic;
    @Column(name = "req_premium_or_platinum", columnDefinition = "JSON")
    private String reqPremiumOrPlatinum;
    @Column(name = "req_super_premium_or_signature", columnDefinition = "JSON")
    private String reqSuperPremiumOrSignature;
    @Column(name = "req_commercial_or_corporate", columnDefinition = "JSON")
    private String reqCommercialOrCorporate;
    @Column(name = "req_international_debit_card", columnDefinition = "JSON")
    private String reqInternationalDebitCard;
    @Column(name = "req_international_credit_card", columnDefinition = "JSON")
    private String reqInternationalCreditCard;
    @Column(name = "req_onus", columnDefinition = "JSON")
    private String reqOnus;
    @Column(name = "req_diners", columnDefinition = "JSON")
    private String reqDiners;
    @Column(name = "req_cashAt_pos", columnDefinition = "JSON")
    private String reqCashAtPos;
    @Column(name = "req_master_pass", columnDefinition = "JSON")
    private String reqMasterPass;
    @Column(name = "req_upi_Credit_Above_2000", columnDefinition = "JSON")
    private String reqUpiCreditAbove2000;
    @Column(name = "req_upi_Credit_Upto_2000", columnDefinition = "JSON")
    private String reqUpiCreditUpto2000;
    @Column(name = "req_upi_Above_2000", columnDefinition = "JSON")
    private String reqUpiAbove2000;
    @Column(name = "req_upi_Upto_2000", columnDefinition = "JSON")
    private String reqUpiUpto2000;
    @Column(name = "req_amex_Domestic ", columnDefinition = "JSON")
    private String reqAmexDomestic;
    @Column(name = "req_amex_International", columnDefinition = "JSON")
    private String reqAmexInternational;
    @Column(name = "app_domestic_debit_upto_2000", columnDefinition = "JSON")
    private String appDomesticDebitUpTo2000;
    @Column(name = "app_domestic_debit_above_2000", columnDefinition = "JSON")
    private String appDomesticDebitAbove2000;
    @Column(name = "app_standard_or_classic", columnDefinition = "JSON")
    private String appStandardOrClassic;
    @Column(name = "app_premium_or_platinum", columnDefinition = "JSON")
    private String appSremiumOrPlatinum;
    @Column(name = "app_super_premium_or_signature", columnDefinition = "JSON")
    private String appSuperPremiumOrSignature;
    @Column(name = "app_commercial_or_corporate", columnDefinition = "JSON")
    private String appCommercialOrCorporate;
    @Column(name = "app_international_debit_card", columnDefinition = "JSON")
    private String appInternationalDebitCard;
    @Column(name = "app_international_credit_card", columnDefinition = "JSON")
    private String appInternationalCreditCard;
    @Column(name = "app_onus", columnDefinition = "JSON")
    private String appOnus;
    @Column(name = "app_diners", columnDefinition = "JSON")
    private String appDiners;
    @Column(name = "app_cashAt_pos", columnDefinition = "JSON")
    private String appCashAtPos;
    @Column(name = "app_master_pass", columnDefinition = "JSON")
    private String appMasterPass;
    @Column(name = "app_upi_Credit_Above_2000", columnDefinition = "JSON")
    private String appUpiCreditAbove2000;
    @Column(name = "app_upi_Credit_Upto_2000", columnDefinition = "JSON")
    private String appUpiCreditUpto2000;
    @Column(name = "app_upi_Above_2000", columnDefinition = "JSON")
    private String appUpiAbove2000;
    @Column(name = "app_upi_Upto_2000", columnDefinition = "JSON")
    private String appUpiUpto2000;
    @Column(name = "app_amex_Domestic ", columnDefinition = "JSON")
    private String appAmexDomestic;
    @Column(name = "app_amex_International", columnDefinition = "JSON")
    private String appAmexInternational;
    private String status;

    private long leadId;
    private boolean pos;
    private boolean sticker;
    private boolean soundBox;
    private int subventionType;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReqDomesticDebitUpTo2000() {
        return reqDomesticDebitUpTo2000;
    }

    public void setReqDomesticDebitUpTo2000(String reqDomesticDebitUpTo2000) {
        this.reqDomesticDebitUpTo2000 = reqDomesticDebitUpTo2000;
    }

    public String getReqDomesticDebitAbove2000() {
        return reqDomesticDebitAbove2000;
    }

    public void setReqDomesticDebitAbove2000(String reqDomesticDebitAbove2000) {
        this.reqDomesticDebitAbove2000 = reqDomesticDebitAbove2000;
    }

    public String getReqStandardOrClassic() {
        return reqStandardOrClassic;
    }

    public void setReqStandardOrClassic(String reqStandardOrClassic) {
        this.reqStandardOrClassic = reqStandardOrClassic;
    }

    public String getReqPremiumOrPlatinum() {
        return reqPremiumOrPlatinum;
    }

    public void setReqPremiumOrPlatinum(String reqPremiumOrPlatinum) {
        this.reqPremiumOrPlatinum = reqPremiumOrPlatinum;
    }

    public String getReqSuperPremiumOrSignature() {
        return reqSuperPremiumOrSignature;
    }

    public void setReqSuperPremiumOrSignature(String reqSuperPremiumOrSignature) {
        this.reqSuperPremiumOrSignature = reqSuperPremiumOrSignature;
    }

    public String getReqCommercialOrCorporate() {
        return reqCommercialOrCorporate;
    }

    public void setReqCommercialOrCorporate(String reqCommercialOrCorporate) {
        this.reqCommercialOrCorporate = reqCommercialOrCorporate;
    }

    public String getReqInternationalDebitCard() {
        return reqInternationalDebitCard;
    }

    public void setReqInternationalDebitCard(String reqInternationalDebitCard) {
        this.reqInternationalDebitCard = reqInternationalDebitCard;
    }

    public String getReqInternationalCreditCard() {
        return reqInternationalCreditCard;
    }

    public void setReqInternationalCreditCard(String reqInternationalCreditCard) {
        this.reqInternationalCreditCard = reqInternationalCreditCard;
    }

    public String getReqOnus() {
        return reqOnus;
    }

    public void setReqOnus(String reqOnus) {
        this.reqOnus = reqOnus;
    }

    public String getReqDiners() {
        return reqDiners;
    }

    public void setReqDiners(String reqDiners) {
        this.reqDiners = reqDiners;
    }

    public String getReqCashAtPos() {
        return reqCashAtPos;
    }

    public void setReqCashAtPos(String reqCashAtPos) {
        this.reqCashAtPos = reqCashAtPos;
    }

    public String getReqMasterPass() {
        return reqMasterPass;
    }

    public void setReqMasterPass(String reqMasterPass) {
        this.reqMasterPass = reqMasterPass;
    }

    public String getReqUpiCreditAbove2000() {
        return reqUpiCreditAbove2000;
    }

    public void setReqUpiCreditAbove2000(String reqUpiCreditAbove2000) {
        this.reqUpiCreditAbove2000 = reqUpiCreditAbove2000;
    }

    public String getReqUpiCreditUpto2000() {
        return reqUpiCreditUpto2000;
    }

    public void setReqUpiCreditUpto2000(String reqUpiCreditUpto2000) {
        this.reqUpiCreditUpto2000 = reqUpiCreditUpto2000;
    }

    public String getReqUpiAbove2000() {
        return reqUpiAbove2000;
    }

    public void setReqUpiAbove2000(String reqUpiAbove2000) {
        this.reqUpiAbove2000 = reqUpiAbove2000;
    }

    public String getReqUpiUpto2000() {
        return reqUpiUpto2000;
    }

    public void setReqUpiUpto2000(String reqUpiUpto2000) {
        this.reqUpiUpto2000 = reqUpiUpto2000;
    }

    public String getReqAmexDomestic() {
        return reqAmexDomestic;
    }

    public void setReqAmexDomestic(String reqAmexDomestic) {
        this.reqAmexDomestic = reqAmexDomestic;
    }

    public String getReqAmexInternational() {
        return reqAmexInternational;
    }

    public void setReqAmexInternational(String reqAmexInternational) {
        this.reqAmexInternational = reqAmexInternational;
    }

    public String getAppDomesticDebitUpTo2000() {
        return appDomesticDebitUpTo2000;
    }

    public void setAppDomesticDebitUpTo2000(String appDomesticDebitUpTo2000) {
        this.appDomesticDebitUpTo2000 = appDomesticDebitUpTo2000;
    }

    public String getAppDomesticDebitAbove2000() {
        return appDomesticDebitAbove2000;
    }

    public void setAppDomesticDebitAbove2000(String appDomesticDebitAbove2000) {
        this.appDomesticDebitAbove2000 = appDomesticDebitAbove2000;
    }

    public String getAppStandardOrClassic() {
        return appStandardOrClassic;
    }

    public void setAppStandardOrClassic(String appStandardOrClassic) {
        this.appStandardOrClassic = appStandardOrClassic;
    }

    public String getAppSremiumOrPlatinum() {
        return appSremiumOrPlatinum;
    }

    public void setAppSremiumOrPlatinum(String appSremiumOrPlatinum) {
        this.appSremiumOrPlatinum = appSremiumOrPlatinum;
    }

    public String getAppSuperPremiumOrSignature() {
        return appSuperPremiumOrSignature;
    }

    public void setAppSuperPremiumOrSignature(String appSuperPremiumOrSignature) {
        this.appSuperPremiumOrSignature = appSuperPremiumOrSignature;
    }

    public String getAppCommercialOrCorporate() {
        return appCommercialOrCorporate;
    }

    public void setAppCommercialOrCorporate(String appCommercialOrCorporate) {
        this.appCommercialOrCorporate = appCommercialOrCorporate;
    }

    public String getAppInternationalDebitCard() {
        return appInternationalDebitCard;
    }

    public void setAppInternationalDebitCard(String appInternationalDebitCard) {
        this.appInternationalDebitCard = appInternationalDebitCard;
    }

    public String getAppInternationalCreditCard() {
        return appInternationalCreditCard;
    }

    public void setAppInternationalCreditCard(String appInternationalCreditCard) {
        this.appInternationalCreditCard = appInternationalCreditCard;
    }

    public String getAppOnus() {
        return appOnus;
    }

    public void setAppOnus(String appOnus) {
        this.appOnus = appOnus;
    }

    public String getAppDiners() {
        return appDiners;
    }

    public void setAppDiners(String appDiners) {
        this.appDiners = appDiners;
    }

    public String getAppCashAtPos() {
        return appCashAtPos;
    }

    public void setAppCashAtPos(String appCashAtPos) {
        this.appCashAtPos = appCashAtPos;
    }

    public String getAppMasterPass() {
        return appMasterPass;
    }

    public void setAppMasterPass(String appMasterPass) {
        this.appMasterPass = appMasterPass;
    }

    public String getAppUpiCreditAbove2000() {
        return appUpiCreditAbove2000;
    }

    public void setAppUpiCreditAbove2000(String appUpiCreditAbove2000) {
        this.appUpiCreditAbove2000 = appUpiCreditAbove2000;
    }

    public String getAppUpiCreditUpto2000() {
        return appUpiCreditUpto2000;
    }

    public void setAppUpiCreditUpto2000(String appUpiCreditUpto2000) {
        this.appUpiCreditUpto2000 = appUpiCreditUpto2000;
    }

    public String getAppUpiAbove2000() {
        return appUpiAbove2000;
    }

    public void setAppUpiAbove2000(String appUpiAbove2000) {
        this.appUpiAbove2000 = appUpiAbove2000;
    }

    public String getAppUpiUpto2000() {
        return appUpiUpto2000;
    }

    public void setAppUpiUpto2000(String appUpiUpto2000) {
        this.appUpiUpto2000 = appUpiUpto2000;
    }

    public String getAppAmexDomestic() {
        return appAmexDomestic;
    }

    public void setAppAmexDomestic(String appAmexDomestic) {
        this.appAmexDomestic = appAmexDomestic;
    }

    public String getAppAmexInternational() {
        return appAmexInternational;
    }

    public void setAppAmexInternational(String appAmexInternational) {
        this.appAmexInternational = appAmexInternational;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getLeadId() {
        return leadId;
    }

    public void setLeadId(long leadId) {
        this.leadId = leadId;
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

    public int getSubventionType() {
        return subventionType;
    }

    public void setSubventionType(int subventionType) {
        this.subventionType = subventionType;
    }
}
