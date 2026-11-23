package com.bijlipay.model.marsdto;

public class BankInformation {
    private BankDetailMars bankDetails;
    private CollectionDetails collectionDetails;

    public BankDetailMars getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetailMars bankDetails) {
        this.bankDetails = bankDetails;
    }

    public CollectionDetails getCollectionDetails() {
        return collectionDetails;
    }

    public void setCollectionDetails(CollectionDetails collectionDetails) {
        this.collectionDetails = collectionDetails;
    }
}