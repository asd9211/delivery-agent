package com.example.deliveryagent.member.exception;

/**
 * 회원이 로그인에 실패했을 때 던지는 예외입니다.
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class LoginFailException extends RuntimeException {
    private static final String MESSAGE = "로그인에 실패하였습니다. ID나 PASSWORD를 확인해주세요!";

    public LoginFailException() {
        super(MESSAGE);
    }
}
