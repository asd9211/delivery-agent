package com.example.deliveryagent.delivery.exception;

/**
 * 배달 기사가 이미 할당되어 있을 때 던지는 예외입니다.
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class AlreadyRiderExistsException extends RuntimeException {
    private static final String MESSAGE = "해당 배달에 이미 배달기사가 지정되었습니다.";

    public AlreadyRiderExistsException() {
        super(MESSAGE);
    }
}
