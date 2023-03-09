package com.example.deliveryagent.rider.presentation;

import com.example.deliveryagent.common.exception.ExceptionResponse;
import com.example.deliveryagent.rider.exception.RiderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class RiderControllerAdvice {

    /**
     * 배달기사를 찾지 못했을 때 던지는 예외를 핸들링합니다.
     *
     * @param e 배달기사를 찾지 못했을 경우
     * @return 예외 메세지와 응답 코드를 리턴
     */
    @ExceptionHandler(RiderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRiderNotFound(RiderNotFoundException e) {
        return ExceptionResponse.from(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
