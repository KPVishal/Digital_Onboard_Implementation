package com.bijlipay.controller;

import com.bijlipay.model.dto.ProxyDto;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/implementation/device")
public class ProxyController {
    @Autowired
    private RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(ProxyController.class);

    @PostMapping("/proxyRequest")
    public ResponseEntity<?> proxyRequest(HttpServletRequest servletRequest,
                                          @RequestBody ProxyDto proxyRequest) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (proxyRequest.getHeaders() != null) {
                proxyRequest.getHeaders().forEach(headers::add);
            }

            Gson gson=new Gson();
            log.info("Header--{}",gson.toJson(proxyRequest));

            HttpEntity<Object> entity = proxyRequest.getPayload() != null
                    ? new HttpEntity<>(proxyRequest.getPayload(), headers)
                    : new HttpEntity<>(headers);

            HttpMethod httpMethod = HttpMethod.resolve(proxyRequest.getMethod().toUpperCase());

            if (httpMethod == null) {
                return ResponseEntity.badRequest().body("Invalid HTTP method: " + proxyRequest.getMethod());
            }


            ResponseEntity<String> response = restTemplate.exchange(
                    proxyRequest.getBaseUrl(),
                    httpMethod,
                    entity,
                    String.class
            );

            log.info("responseCode--{}",gson.toJson(response.getBody()));

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("HTTP error from downstream: {}", ex.getResponseBodyAsString());

            return ResponseEntity.status(ex.getStatusCode())
                    .body(ex.getResponseBodyAsString());

        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
}
