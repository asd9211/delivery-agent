package com.example.deliveryagent.member.application;

import com.example.deliveryagent.member.entity.MockMember;
import com.example.deliveryagent.member.presentation.dto.request.LoginDto;
import com.example.deliveryagent.member.presentation.dto.request.MemberCreateDto;
import com.example.deliveryagent.member.domain.Member;
import com.example.deliveryagent.member.exception.LoginFailException;
import com.example.deliveryagent.member.exception.PasswordValidateException;
import com.example.deliveryagent.member.exception.UserNotFoundException;
import com.example.deliveryagent.member.infra.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DisplayName("회원 서비스 테스트")
class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_조회_테스트 {
        @Test
        void 모든_회원_조회_성공() {
            // given
            Member member1 = member("test1");
            Member member2 = member("test2");

            doReturn(Arrays.asList(member1, member2)).when(memberRepository).findAll();

            // when
            List<Member> memberList = memberService.findAll();

            // then
            assertTrue(memberList.size() > 1);
        }

        @Test
        void 회원아이디로_회원_조회_성공() {
            // given
            String userId = "test1";
            Member member1 = member(userId);

            doReturn(Optional.of(member1)).when(memberRepository).findByUserId(userId);

            // when
            Member member = memberService.findByUserId(userId);

            // then
            assertEquals(userId, member.getUserId());
        }

        @Test
        void 회원아이디로_회원_조회_실패() {
            // given
            String userId = "test1";

            doReturn(Optional.empty()).when(memberRepository).findByUserId(userId);

            // when
            UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> memberService.findByUserId(userId));

            // then
            String expectedMessage = "ID와 일치하는 회원이 없습니다.";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 회원_등록_테스트 {
        @Test
        void 회원가입_성공() {
            // given
            String userId = "test";
            String password = "1a2a3a4a5a!@#";
            String name = "홍길동";

            MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                    .userId(userId)
                    .password(password)
                    .name(name)
                    .build();

            doReturn(MockMember.create()).when(memberRepository).save(any(Member.class));

            // when
            Member resMember = memberService.create(memberCreateDto);

            // then
            assertTrue(resMember.getId() != null);
        }

        @Test
        void 회원가입_실패_패스워드_12자리_미만() {
            // given
            String userId = "test";
            String password = "1a2a3a!@#";
            String name = "홍길동";

            MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                    .userId(userId)
                    .password(password)
                    .name(name)
                    .build();

            // when
            PasswordValidateException e = assertThrows(PasswordValidateException.class, () -> memberService.create(memberCreateDto));

            // then
            String expectedMessage = "비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성해야 합니다.";
            assertEquals(expectedMessage, e.getMessage());
        }

        @Test
        void 회원가입_실패_비밀번호조합_조건미달_숫자_영어소문자() {
            // given
            String userId = "test";
            String password = "1a2a3a";
            String name = "홍길동";

            MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                    .userId(userId)
                    .password(password)
                    .name(name)
                    .build();

            // when
            PasswordValidateException e = assertThrows(PasswordValidateException.class, () -> memberService.create(memberCreateDto));

            // then
            String expectedMessage = "비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성해야 합니다.";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 로그인_테스트 {
        @Test
        void 로그인_성공() {
            // given
            String userId = "test";
            String password = "1a2a3a4a5a!@#";

            LoginDto loginDto = LoginDto.builder()
                    .userId(userId)
                    .password(password)
                    .build();

            doReturn(Optional.of(loginDto.toEntity())).when(memberRepository).findByUserIdAndPassword(userId, password);

            // when
            Member member = memberService.login(loginDto);

            // then
            assertEquals(userId, member.getUserId());
        }

        @Test
        void 로그인_실패() {
            // given
            String userId = "test";
            String password = "1a2a3a4a5a!@#";

            LoginDto loginDto = LoginDto.builder()
                    .userId(userId)
                    .password(password)
                    .build();

            doReturn(Optional.empty()).when(memberRepository).findByUserIdAndPassword(userId, password);

            // when
            LoginFailException e = assertThrows(LoginFailException.class, () -> memberService.login(loginDto));

            // then
            String expectedMessage = "로그인에 실패하였습니다. ID나 PASSWORD를 확인해주세요!";
            assertEquals(expectedMessage, e.getMessage());
        }
    }



    Member member(String userId) {
        return new Member().builder()
                .userId(userId)
                .name("홍길동")
                .build();
    }

}