package com.bijlipay.model.dto;

import com.bijlipay.model.LeadCancellationReason;

public class DocumentUpdateDto {
    private String cancellationReason;
    private String documentRemarks;

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public String getDocumentRemarks() {
        return documentRemarks;
    }

    public void setDocumentRemarks(String documentRemarks) {
        this.documentRemarks = documentRemarks;
    }
}
