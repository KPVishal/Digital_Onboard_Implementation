package com.bijlipay.controller;

import com.bijlipay.model.LeadInformation;
import com.bijlipay.model.User;
import com.bijlipay.model.UserAuthentication;
import com.bijlipay.model.dto.AggLeadApproveRequest;
import com.bijlipay.service.LeadInformationService;
import com.bijlipay.service.UserAuthenticationService;
import com.bijlipay.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/implementation/api")
@Slf4j
public class OpsAmlIntegrationController extends BaseController{
    @Value("${aml.base.url}")
    private String amlBaseUrl;

    @Value("${aml.base.check.status}")
    private String amlCheckStatus;

    @Value("${aml.api.key}") // Configured in application.properties / environment variable OPS_API_KEY
    private String amlApiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserAuthenticationService userAuthenticateService;

    @Autowired
    private LeadInformationService leadInformationService;

    // This Api is Proxy
    @PostMapping("/aml-check")
    public ResponseEntity<?> proxyAmlCheck(@RequestBody Map<String, Object> payload) {
        String targetUrl = amlBaseUrl;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Key", amlApiKey);

        log.info("Ops Aml url --- :{}",targetUrl=!Boolean.parseBoolean(null) ?targetUrl:"NA");
        JSONObject jsonObject =new JSONObject(payload);
        log.info("The Request Payload --->{}",jsonObject!=null ? jsonObject:"NA");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    targetUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            log.info("Aml Response Status---------:{}",response.getStatusCode());
            log.info("Aml Response Body---------:{}",response.getBody());
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpStatusCodeException ex) {
            log.info("Http status exception ---{}",ex.getStatusCode());
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        }catch (Exception e){
            log.info("Exception ----{}",e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Error: " + e.getMessage());
        }
    }

    // Get the Status For the Api
    @GetMapping("/aml-check/{lead_id}")
    public ResponseEntity<?> proxyCheckStatus(@PathVariable("lead_id") String leadId) {
        log.info("Lead id ---: {}", leadId != null ? leadId : "NA");

        String targetUrl = amlCheckStatus + "/" + leadId;
        log.info("Ops Aml check status url --- : {}", targetUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Key", amlApiKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    targetUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            log.info("Aml Response Status---------: {}", response.getStatusCode());
            log.info("Aml Response Body---------: {}", response.getBody());
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (HttpStatusCodeException ex) {
            log.info("Http status exception --- {}", ex.getStatusCode());
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());

        } catch (Exception e) {
            log.info("Exception ---- {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    /**
     * This For the Aml check status Update our lead information Table.
     * Lead Id
     */

    @PostMapping("/amlStatusUpdate/{leadId}")
    public  ResponseEntity<?>updataAmlStatus(@PathVariable Long leadId, @RequestBody AggLeadApproveRequest data, HttpServletRequest request){
        String token =request.getHeader("Authorization");
        log.info("Http request token ------->{}",token!=null?token:"");
        User curuser = getSessionUser();
        log.info("Session user ---->{}",curuser!=null?curuser:"NA");
        UserAuthentication userauth = userAuthenticateService.getToken(curuser.getId());
        if (token.equals(userauth.getToken())) {
            log.info("Lead Id  for AML status------>{}",leadId!=null?leadId:"NA");
            LeadInformation leadInformation = leadInformationService.findLead(leadId);
           try {
               if(leadInformation!=null){
                   Pair<Integer, Object> lead = leadInformationService.amlStatusUpdated(leadInformation,data);
                   if (lead.getFirst() == 1) {
                       return new ResponseEntity<>(new ApiResponse(OK, "success", lead.getSecond()),HttpStatus.OK);
                   }
               }
               return new ResponseEntity<>(new ApiResponse(NOT_FOUND, "Lead not found"), HttpStatus.NOT_FOUND);

           }catch (Exception e){
               log.info("Exception -->{}",e.getMessage());
               e.printStackTrace();
               return new ResponseEntity<>(new ApiResponse(INTERNAL_SERVER_ERROR, "Error!!!", e.getMessage()), INTERNAL_SERVER_ERROR);
           }
        }
        return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED, "UnAuthorized!!!", null), HttpStatus.UNAUTHORIZED);

    }

}

