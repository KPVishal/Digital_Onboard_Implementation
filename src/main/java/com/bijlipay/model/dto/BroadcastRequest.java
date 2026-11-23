package com.bijlipay.model.dto;

import lombok.Data;

@Data
public class BroadcastRequest {
    private String role;
    private PushPayloadDto payload;
}
