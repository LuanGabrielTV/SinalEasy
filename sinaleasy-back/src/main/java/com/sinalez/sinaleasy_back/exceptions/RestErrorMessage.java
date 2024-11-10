package com.sinalez.sinaleasy_back.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestErrorMessage {
    private HttpStatus status;
    private String message;

    public RestErrorMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }



}
