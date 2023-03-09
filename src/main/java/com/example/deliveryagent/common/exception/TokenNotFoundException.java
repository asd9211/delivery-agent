package com.example.deliveryagent.common.exception;

/**
 * 토큰을 찾을 수 없을 때 던지는 예외입니다.
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class TokenNotFoundException extends RuntimeException {
    private static final String MESSAGE = "요청 토큰을 찾을 수 없습니다.";

    public TokenNotFoundException() {
        super(MESSAGE);
    }
}
