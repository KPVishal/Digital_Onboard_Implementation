package com.bijlipay.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private HttpStatus status;
    private String message;
    private Object data;
    public ApiResponse(){
        this.status=null;
        this.message=null;
        this.data=null;
    }

    public ApiResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponse(HttpStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
