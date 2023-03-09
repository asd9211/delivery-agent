package com.example.deliveryagent.delivery.presentation;

import com.example.deliveryagent.common.exception.ExceptionResponse;
import com.example.deliveryagent.delivery.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class DeliveryControllerAdvice {

    /**
     * 주소변경에 실패했을때 던지는 예외를 핸들링합니다.
     *
     * @param e 주소변경에 실패했을 경우
     * @return 예외 메세지와 응답 코드를 리턴
     */
    @ExceptionHandler(AddressImmutableException.class)
    public ResponseEntity<Map<String, Object>> handleChageAddressFail(AddressImmutableException e) {
        return ExceptionResponse.from(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 배달정보를 찾지 못했을 때 던지는 예외를 핸들링합니다.
     *
     * @param e 배달정보를 찾지 못했을 경우
     * @return 예외 메세지와 응답 코드를 리턴
     */
    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDeliveryNotFound(DeliveryNotFoundException e) {
        return ExceptionResponse.from(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 이미 배달 기사가 배정되어 있을 때 던지는 예외를 핸들링합니다.
     *
     * @param e 이미 배달 기사가 배정되어 있을 경우
     * @return 예외 메세지와 응답 코드를 리턴
     */
    @ExceptionHandler(AlreadyRiderExistsException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyRiderExists(AlreadyRiderExistsException e) {
        return ExceptionResponse.from(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 배달 상태코드를 찾지 못했을 때 던지는 예외를 핸들링합니다.
     *
     * @param e 배달 상태코드를 찾지 못했을 경우
     * @return 예외 메세지와 응답 코드를 리턴
     */
    @ExceptionHandler(DeliveryStatusNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDeliveryStatusNotFound(DeliveryStatusNotFoundException e) {
        return ExceptionResponse.from(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 조회 일자의 범위가 초과하였을 때 던지는 예외를 핸들링합니다.
     *
     * @param e 조회 일자의 범위가 초과하였을 경우
     * @return 예외 메세지와 응답 코드를 리턴
     */
    @ExceptionHandler(DateOutOfRangeException.class)
    public ResponseEntity<Map<String, Object>> handleDateOutOfRange(DateOutOfRangeException e) {
        return ExceptionResponse.from(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
