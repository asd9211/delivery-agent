package com.example.deliveryagent.member.application;

import com.example.deliveryagent.member.domain.Member;
import com.example.deliveryagent.member.exception.LoginFailException;
import com.example.deliveryagent.member.exception.PasswordValidateException;
import com.example.deliveryagent.member.exception.UserDuplicatedException;
import com.example.deliveryagent.member.exception.UserNotFoundException;
import com.example.deliveryagent.member.infra.MemberRepository;
import com.example.deliveryagent.member.presentation.dto.request.LoginDto;
import com.example.deliveryagent.member.presentation.dto.request.MemberCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원리스트를 조회합니다.
     *
     * @return 회원정보 리스트
     */
    public List<Member> findAll() {
        return memberRepository
                .findAll();
    }

    /**
     * 회원정보를 조회합니다.
     *
     * @param memberId 회원 식별자
     * @return 회원정보
     */
    public Member findById(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * 회원정보를 조회합니다.
     *
     * @param userId 회원 아이디
     * @return 회원정보
     */
    public Member findByUserId(String userId) {
        return memberRepository
                .findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * 회원를 등록합니다.
     *
     * @param memberCreateDto 등록할 회원 정보
     * @return boolean
     */
    public Member create(MemberCreateDto memberCreateDto) {
        validateDuplicatedUserId(memberCreateDto);
        validatePassword(memberCreateDto);
        Member member = memberCreateDto.toEntity();
        return memberRepository.save(member);
    }

    /**
     * 로그인을 합니다.
     *
     * @param loginDto 로그인할 회원 정보
     * @return 회원정보
     */
    public Member login(LoginDto loginDto) {
        return memberRepository
                .findByUserIdAndPassword(loginDto.getUserId(), loginDto.getPassword())
                .orElseThrow(LoginFailException::new);
    }

    /**
     * 회원아이디의 중복을 검사합니다.
     *
     * @param memberCreateDto 등록할 회원 정보
     */
    private void validateDuplicatedUserId(MemberCreateDto memberCreateDto) {
        if (memberRepository.existsByUserId(memberCreateDto.getUserId())) {
            throw new UserDuplicatedException();
        }
    }

    /**
     * 패스워드 정책에 부합하는지 검사합니다.
     * <p>
     * 12자리 이상의 문자열
     * 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상
     *
     * @param memberCreateDto 회원가입할 회원 정보
     */
    private void validatePassword(MemberCreateDto memberCreateDto) {
        String password = memberCreateDto.getPassword();
        int passCount = 0;              // password 정책 통과 갯수
        int passCountLimit = 3;         // password 정책 최소 통과 갯수
        int passwordLengthLimit = 12;   // password 길이 제한

        if (containLowerCase(password)) {
            passCount++;
        }

        if (containUpperCase(password)) {
            passCount++;
        }

        if (containNumber(password)) {
            passCount++;
        }

        if (containsSpecialChar(password)) {
            passCount++;
        }

        if (passCount < passCountLimit || password.length() < passwordLengthLimit) {
            throw new PasswordValidateException();
        }
    }

    private boolean containLowerCase(String password) {
        return password.matches(".*[a-z].*");
    }

    private boolean containUpperCase(String password) {
        return password.matches(".*[A-Z].*");
    }

    private boolean containNumber(String password) {
        return password.matches(".*\\d.*");
    }

    private boolean containsSpecialChar(String password) {
        String specialCharPattern = "[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]";
        return password.matches(".*" + specialCharPattern + ".*");
    }
}
