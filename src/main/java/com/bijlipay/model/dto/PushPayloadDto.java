package com.bijlipay.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushPayloadDto {

    private NotificationDto notification;
    private DataDto data;

//    public void setNotification(String payload) {
//    }
//
//    public void setData(String payload) {
//    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationDto {
        private String title;
        private String body;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataDto {
        private String navigateTo;
    }
}
