package com.bijlipay.model.dto;

import com.bijlipay.model.MarsDeviceDetails;
import com.bijlipay.model.UploadedDocuments;

import java.util.List;

public class ImplementDetailsDto {

    private MarsDeviceDetails marsDeviceDetails;
    private List<MarsDeviceDetails> marsDeviceDetailsList;
    private List<UploadedDocuments> implementationDocuments;
    private List<UploadedDocuments> implementationRejectDocuments;

    public MarsDeviceDetails getMarsDeviceDetails() {
        return marsDeviceDetails;
    }

    public void setMarsDeviceDetails(MarsDeviceDetails marsDeviceDetails) {
        this.marsDeviceDetails = marsDeviceDetails;
    }

    public List<MarsDeviceDetails> getMarsDeviceDetailsList() {
        return marsDeviceDetailsList;
    }

    public void setMarsDeviceDetailsList(List<MarsDeviceDetails> marsDeviceDetailsList) {
        this.marsDeviceDetailsList = marsDeviceDetailsList;
    }

    public List<UploadedDocuments> getImplementationDocuments() {
        return implementationDocuments;
    }

    public void setImplementationDocuments(List<UploadedDocuments> implementationDocuments) {
        this.implementationDocuments = implementationDocuments;
    }

    public List<UploadedDocuments> getImplementationRejectDocuments() {
        return implementationRejectDocuments;
    }

    public void setImplementationRejectDocuments(List<UploadedDocuments> implementationRejectDocuments) {
        this.implementationRejectDocuments = implementationRejectDocuments;
    }
}
