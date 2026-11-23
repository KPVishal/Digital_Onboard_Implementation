package com.bijlipay.config;


import com.bijlipay.model.Role;
import com.bijlipay.model.dto.BroadcastRequest;
import com.bijlipay.model.dto.RoleBroadcastResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "onboardLead")
public interface NotificationFeignClient {

//    @PostMapping("/lead/pushnotify/notification/broadcast-submit")
//    RoleBroadcastResponse broadcastSubmit(@RequestBody BroadcastRequest request);

    @PostMapping("/lead/pushnotify/notification/broadcast-submit")
    RoleBroadcastResponse broadcastSubmit(
            @RequestHeader("Authorization") String authToken,
            @RequestBody BroadcastRequest request);

//    @PostMapping("/lead/pushnotify/notification/broadcast-techissue")
//    RoleBroadcastResponse broadcastTechissue(@RequestBody BroadcastRequest request);

    @PostMapping("/lead/pushnotify/notification/broadcast-submit")
    RoleBroadcastResponse broadcastTechissue(
            @RequestHeader("Authorization") String authToken,
            @RequestBody BroadcastRequest request);
}