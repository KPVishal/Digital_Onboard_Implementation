package com.bijlipay.model.marsdto;

public class CollectionDetails {
    private String acquirerBank;
    private String chequeAmount;
    private String chequeDate;
    private String chequeDepositedDate;
    private String chequeNumber;
    private String collectedDate;
    private String swipeAmount;
    private String swipeTerminal;
    private String upiLink;

    public String getUpiLink() {
        return upiLink;
    }

    public void setUpiLink(String upiLink) {
        this.upiLink = upiLink;
    }

    public String getAcquirerBank() {
        return acquirerBank;
    }

    public void setAcquirerBank(String acquirerBank) {
        this.acquirerBank = acquirerBank;
    }

    public String getChequeAmount() {
        return chequeAmount;
    }

    public void setChequeAmount(String chequeAmount) {
        this.chequeAmount = chequeAmount;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getChequeDepositedDate() {
        return chequeDepositedDate;
    }

    public void setChequeDepositedDate(String chequeDepositedDate) {
        this.chequeDepositedDate = chequeDepositedDate;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getCollectedDate() {
        return collectedDate;
    }

    public void setCollectedDate(String collectedDate) {
        this.collectedDate = collectedDate;
    }

    public String getSwipeAmount() {
        return swipeAmount;
    }

    public void setSwipeAmount(String swipeAmount) {
        this.swipeAmount = swipeAmount;
    }

    public String getSwipeTerminal() {
        return swipeTerminal;
    }

    public void setSwipeTerminal(String swipeTerminal) {
        this.swipeTerminal = swipeTerminal;
    }
}
