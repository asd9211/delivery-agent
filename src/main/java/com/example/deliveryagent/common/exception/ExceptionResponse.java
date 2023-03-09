package com.example.deliveryagent.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ExceptionResponse {

    private ExceptionResponse(){}
    public static ResponseEntity<Map<String, Object>> from(RuntimeException e, HttpStatus status){
        return new ResponseEntity<>(Map.of("message", e.getMessage()), status);
    }
}
