package com.example.deliveryagent.member.exception;

/**
 * 회원아이디가 이미 존재할 때 던지는 예외입니다.
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class UserDuplicatedException extends RuntimeException {
    private static final String MESSAGE = "이미 존재하는 ID가 있습니다.";

    public UserDuplicatedException() {
        super(MESSAGE);
    }
}
