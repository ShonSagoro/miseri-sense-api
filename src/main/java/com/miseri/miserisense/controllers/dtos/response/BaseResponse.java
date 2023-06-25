package com.miseri.miserisense.controllers.dtos.response;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class BaseResponse {

    private Object data;

    private String message;

    private Boolean success;

    private HttpStatus httpStatus;

    public ResponseEntity<BaseResponse> apply() {
        return new ResponseEntity<>(this, httpStatus);
    }
}
