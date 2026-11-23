package com.bijlipay.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AggLeadApproveRequest {
    private String merchantCategoryCode;
    private String amlStatus;

    public String getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }

    public String getAmlStatus() {
        return amlStatus;
    }

    public void setAmlStatus(String amlStatus) {
        this.amlStatus = amlStatus;
    }


}
