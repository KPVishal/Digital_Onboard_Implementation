package com.bijlipay.model.dto;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OnboardingRequestSou {
    private String deviceSerialNo;
    private String deviceType;
    private String tid;
    private String mid;
    private List<String> vpaId;
    private String merchantName;
    private String leadSource;
    private String mcc;
    private String bpRegion;
    private String city;
    private Long languageId;
    private Long aggregatorId;
    private Long office;
    private Long marketingId;
    private String settlementVpa;
}
