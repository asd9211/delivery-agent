package com.example.deliveryagent.member.exception;

/**
 * 회원이 로그인에 실패했을 때 던지는 예외입니다.
 * RuntimeException을 상속받으므로 예외 발생 시 roll-back을 수행합니다.
 */
public class PasswordValidateException extends RuntimeException {
    private static final String MESSAGE = "비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성해야 합니다.";

    public PasswordValidateException() {
        super(MESSAGE);
    }
}
