package com.example.deliveryagent.delivery.exception;

/**
 * 배달 정보를 찾지 못했을 때 던지는 예외입니다.
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class DeliveryNotFoundException extends RuntimeException {
    private static final String MESSAGE = "정보와 일치하는 배달정보가 없습니다.";

    public DeliveryNotFoundException() {
        super(MESSAGE);
    }
}
