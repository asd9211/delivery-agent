package com.example.deliveryagent.delivery.exception;

/**
 * 배달 상태코드를 찾지 못했을 때 던지는 예외입니다.
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class DeliveryStatusNotFoundException extends RuntimeException {
    private static final String MESSAGE = "일치하는 배달상태가 없습니다.";

    public DeliveryStatusNotFoundException() {
        super(MESSAGE);
    }
}
