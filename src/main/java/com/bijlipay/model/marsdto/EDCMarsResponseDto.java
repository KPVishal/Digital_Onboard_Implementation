package com.bijlipay.model.marsdto;

import org.springframework.http.HttpStatus;

public class EDCMarsResponseDto {

    int code;
    HttpStatus status;
    EdcResponseDto data;
    String message;

    public EdcResponseDto getData() {
        return data;
    }

    public void setData(EdcResponseDto data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
}
