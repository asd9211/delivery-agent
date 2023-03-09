package com.example.deliveryagent.delivery.exception;

/**
 * 배달 주소 변경이 불가능할 때 던지는 예외입니다.
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class AddressImmutableException extends RuntimeException {
    private static final String MESSAGE = "배달기사가 확정되어 목적지 변경이 불가능합니다.";

    public AddressImmutableException() {
        super(MESSAGE);
    }
}
