package com.bijlipay.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleBroadcastResponse {
    private String role;
    private int totalUsersWithRole;
    private int sent;
    private int skippedNoToken;
    private int failed;
    private List<BroadcastResult> details;
}
