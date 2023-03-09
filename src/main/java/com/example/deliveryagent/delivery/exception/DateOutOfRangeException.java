package com.example.deliveryagent.delivery.exception;

/**
 * 조회 일자의 범위가 초과하였을 때 던지는 예외입니다.
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class DateOutOfRangeException extends RuntimeException {
    private static final String MESSAGE = "조회할 수 있는 최대 범위는 3일입니다.";

    public DateOutOfRangeException() {
        super(MESSAGE);
    }
}
