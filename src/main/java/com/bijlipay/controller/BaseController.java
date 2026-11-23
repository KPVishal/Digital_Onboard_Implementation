package com.bijlipay.controller;

import com.bijlipay.config.security.JwtUtil;
import com.bijlipay.model.LeadInformation;
import com.bijlipay.model.Notification;
import com.bijlipay.model.User;
import com.bijlipay.model.dto.LeadDto;
import com.bijlipay.model.dto.NotificationDto;
import com.bijlipay.model.dto.PushNotificationResponseDto;
import com.bijlipay.repository.LeadInformationRepository;
import com.bijlipay.repository.NotificationRepository;
import com.bijlipay.repository.UserAuthenticationRepo;
import com.bijlipay.repository.UserRepository;
import com.bijlipay.service.AndroidPushNotificationsService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;
    @Autowired
    private UserRepository userRepository;

    @Value("${tsdb.datasource.url}")
    private String url;

    @Value("${tsdb.datasource.username}")
    private String username;

    @Value("${tsdb.datasource.password}")
    private String password;
    @Autowired
    private LeadInformationRepository leadInformationRepository;
    @Autowired
    private UserAuthenticationRepo userAuthenticationRepo;
    @Autowired
    private JwtUtil util;

//    public User getSessionUser() {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        LOGGER.info("Get current session user : ------- ID : {}, name : ------- : {}", user.getId(), user.getName());
//        return user;
//    }

    @Async
    public void sendPushNotification(String title, String reason, LeadInformation leadInformation, String redirectTo) {
        sendPushNotificationGenerate(title, reason, leadInformation, redirectTo, leadInformation.getCreatedBy());
    }
    @Async
    public void sendPushNotificationConsentLink(String title, String reason, LeadInformation leadInformation, String redirectTo) {
        sendPushNotificationGenerateConsentLink(title, reason, leadInformation, redirectTo, leadInformation.getCreatedBy());
    }

    @Async
    public void sendPushNotificationGenerate(String title, String reason, LeadInformation leadInformation, String redirectTo, User userToNotify) {
        Gson gson = new Gson();
        PushNotificationResponseDto<LeadDto> pushNotificationResponseDto = new PushNotificationResponseDto();
        pushNotificationResponseDto.setNotification(new NotificationDto(title, reason));
        LeadDto leadDto = new LeadDto();
        leadDto.setLeadId(leadInformation.getLeadId());
        leadDto.setLeadStatus(leadInformation.getLeadStatus().getLeadStatusId());
        leadDto.setLeadNumber(leadInformation.getLeadNumber());
        leadDto.setRedirectTo(redirectTo);
        String pushData = gson.toJson(leadDto);
        Map<String, String> dataElement = new HashMap<>();
        dataElement.put("keysandvalues", pushData);
        pushNotificationResponseDto.setData(dataElement);
        pushNotificationResponseDto.setToken(userToNotify != null ? userToNotify.getDeviceToken() : null);
        Notification notification = new Notification();
        notification.setNotificationTitle(title);
        notification.setNotificationReason(reason);
        notification.setNotificationRedirectTo(redirectTo);
        notification.setNotificationRegion(leadInformation.getRegion());
        notification.setUser(userToNotify);
        notification.setData(new Gson().toJson(leadDto));
        Notification afterNotificationSave = notificationRepository.save(notification);
        leadDto.setNotificationId(afterNotificationSave.getId());

        try {
            Map<String, PushNotificationResponseDto> result = new HashMap<>();
            result.put("message", pushNotificationResponseDto);
            String payload = gson.toJson(result);
            String firebaseResponse = this.sendPushNotification(payload);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendPushNotificationGenerateConsentLink(String title, String reason, LeadInformation leadInformation, String redirectTo, User userToNotify) {
        //this method is used for only consent-link
        Gson gson = new Gson();
        PushNotificationResponseDto<LeadDto> pushNotificationResponseDto = new PushNotificationResponseDto();
        LeadDto leadDto = new LeadDto();
        leadDto.setLeadId(leadInformation.getLeadId());
        leadDto.setLeadStatus(leadInformation.getLeadStatus().getLeadStatusId());
        leadDto.setLeadNumber(leadInformation.getLeadNumber());
        leadDto.setRedirectTo(redirectTo);
        String pushData = gson.toJson(leadDto);
        Map<String, String> dataElement = new HashMap<>();
        dataElement.put("keysandvalues", pushData);
        pushNotificationResponseDto.setData(dataElement);
        pushNotificationResponseDto.setToken(userToNotify != null ? userToNotify.getDeviceToken() : null);
        Notification notification = new Notification();
        notification.setNotificationTitle(title);
        notification.setNotificationReason(reason);
        notification.setNotificationRedirectTo(redirectTo);
        notification.setNotificationRegion(leadInformation.getRegion());
        notification.setUser(userToNotify);
        notification.setData(new Gson().toJson(leadDto));
        Notification afterNotificationSave = notificationRepository.save(notification);
        leadDto.setNotificationId(afterNotificationSave.getId());

        try {
            Map<String, PushNotificationResponseDto> result = new HashMap<>();
            result.put("message", pushNotificationResponseDto);
            String payload = gson.toJson(result);
            String firebaseResponse = this.sendPushNotification(payload);
        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public String sendPushNotification(String bodyRequest) throws ExecutionException, InterruptedException, IOException {
        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(bodyRequest);
        String firebaseResponse = pushNotification.get();
        LOGGER.info("Firebase Response --{}", firebaseResponse);
        return firebaseResponse;
    }

    public User getSessionUser() {
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // log.info("The session user ===========user id  {}, user name {}",user.getId(),user.getName());
        return userRepository.findUsername(user);
    }

    public Connection getConnectionTSDB() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}


