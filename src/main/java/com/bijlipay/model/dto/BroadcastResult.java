package com.bijlipay.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastResult {
    private String userId;
    private String status;   // SENT | SKIPPED_NO_TOKEN | FAILED_INVALID_TOKEN | FAILED_OTHER
    private String message;
}
