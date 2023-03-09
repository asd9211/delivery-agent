package com.example.deliveryagent.member.presentation;

import com.example.deliveryagent.common.exception.ExceptionResponse;
import com.example.deliveryagent.member.exception.LoginFailException;
import com.example.deliveryagent.member.exception.PasswordValidateException;
import com.example.deliveryagent.member.exception.UserDuplicatedException;
import com.example.deliveryagent.member.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class MemberControllerAdvice {

    /**
     * 로그인에 실패했을때 던지는 예외를 핸들링합니다.
     *
     * @param e 로그인에 실패했을경우
     * @return 예외 메세지와 응답 코드를 리턴
     */
    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<Map<String, Object>> handleLoginFail(LoginFailException e) {
        return ExceptionResponse.from(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 회원가입시 패스워드 정책에 부합하지 않을 경우 던지는 예외를 핸들링합니다.
     *
     * @param e 패스워드 정책에 부합하지 않을 경우
     * @return 예외 메세지와 응답 코드를 리턴
     */
    @ExceptionHandler(PasswordValidateException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidPasswordPolicy(PasswordValidateException e) {
        return ExceptionResponse.from(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 유저를 찾지 못했을 때 던지는 예외를 핸들링합니다.
     *
     * @param e 패스워드 정책에 부합하지 않을 경우
     * @return 예외 메세지와 응답 코드를 리턴
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException e) {
        return ExceptionResponse.from(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 유저아이디가 중복되었을 때 던지는 예외를 핸들링합니다.
     *
     * @param e 유저아이디가 중복되었을 경우
     * @return 예외 메세지와 응답 코드를 리턴
     */
    @ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity<Map<String, Object>> handleUserDupplicated(UserDuplicatedException e) {
        return ExceptionResponse.from(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
