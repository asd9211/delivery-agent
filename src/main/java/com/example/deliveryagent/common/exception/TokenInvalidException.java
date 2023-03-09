package com.example.deliveryagent.common.exception;

/**
 * 요청 토큰이 만료되었을 때 던지는 예외입니다.
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class TokenInvalidException extends RuntimeException {
    private static final String MESSAGE = "토큰이 더 이상 유효하지 않습니다.";

    public TokenInvalidException() {
        super(MESSAGE);
    }
}
