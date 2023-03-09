package com.example.deliveryagent.rider.exception;

/**
 * 배달기사를 찾지 못했을 때 던지는 예외입니다.
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class RiderNotFoundException extends RuntimeException {
    private static final String MESSAGE = "정보와 일치하는 배달기사가 없습니다.";

    public RiderNotFoundException() {
        super(MESSAGE);
    }
}
