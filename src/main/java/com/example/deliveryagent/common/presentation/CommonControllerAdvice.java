package com.example.deliveryagent.common.presentation;

import com.example.deliveryagent.common.exception.ExceptionResponse;
import com.example.deliveryagent.common.exception.TokenInvalidException;
import com.example.deliveryagent.common.exception.TokenNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CommonControllerAdvice {

    /**
     * 토큰이 유효하지 않을 때 던지는 예외를 핸들링합니다.
     *
     * @param e 토큰이 유효하지 않을 경우
     * @return 예외 메세지와 응답 코드를 리턴
     */
    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<Map<String, Object>> handleChageAddressFail(TokenInvalidException e) {
        return ExceptionResponse.from(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 토큰을 찾지 못했을 때 던지는 예외를 핸들링합니다.
     *
     * @param e HttpHeader에 토큰이 없을 경우
     * @return 예외 메세지와 응답 코드를 리턴
     */
    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDeliveryNotFound(TokenNotFoundException e) {
        return ExceptionResponse.from(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
