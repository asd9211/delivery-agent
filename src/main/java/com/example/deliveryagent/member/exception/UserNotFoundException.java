package com.example.deliveryagent.member.exception;

/**
 * 회원을 찾지 못했을 때 던지는 예외입니다.
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "ID와 일치하는 회원이 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
