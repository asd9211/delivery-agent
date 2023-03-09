package com.example.deliveryagent.member.presentation;

import com.example.deliveryagent.member.application.MemberService;
import com.example.deliveryagent.member.presentation.dto.request.LoginDto;
import com.example.deliveryagent.member.presentation.dto.request.MemberCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("회원 API 테스트")
class MemberControllerTest {

    private static String apiUrl = "/member";

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class 회원_등록_API는 {

        @Nested
        class 올바른_회원정보가_주어지면 {

            @Test
            void 회원을_저장하고_회원정보와_상태코드200을_리턴한다() throws Exception {
                // given
                MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                        .name("정대만")
                        .password("12345qwert!@#")
                        .userId("test")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(memberCreateDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().string(containsString("userId")));
            }
        }

        @Nested
        class 유효하지_않은_회원정보가_주어지면 {

            @Test
            void 아이디가_빈값이면_400을_리턴한다() throws Exception {
                // given
                MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                        .name("정대만")
                        .password("12345qwert!@#")
                        .userId("")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(memberCreateDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
            }

            @Test
            void 아이디가_중복이면_500을_리턴한다() throws Exception {
                // given
                MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                        .name("정대만")
                        .password("12345qwert!@#")
                        .userId("test")
                        .build();

                memberService.create(memberCreateDto);

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(memberCreateDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isInternalServerError());
            }

            @Test
            void 패스워드가_빈값이면_400을_리턴한다() throws Exception {
                // given
                MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                        .name("정대만")
                        .password("")
                        .userId("test")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(memberCreateDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
            }

            @Test
            void 패스워드가_12자_미만일경우_500을_리턴한다() throws Exception {
                // given
                MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                        .name("정대만")
                        .password("12345qwert!")
                        .userId("test")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(memberCreateDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isInternalServerError());
            }

            @Test
            void 패스워드_정책에_부합하지않을경우_500을_리턴한다() throws Exception {
                // given
                MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                        .name("정대만")
                        .password("12345qwert12")
                        .userId("test")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(memberCreateDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isInternalServerError());
            }

            @Test
            void 이름이_빈값이면_400을_리턴한다() throws Exception {
                // given
                MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                        .name("")
                        .password("12345qwert!@#")
                        .userId("test")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(memberCreateDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
            }
        }
    }

    @Nested
    class 회원_로그인_API는 {

        @BeforeEach
        void signUp() {
            MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                    .name("정대만")
                    .userId("test")
                    .password("1234567890qwe!@#")
                    .build();
            memberService.create(memberCreateDto);
        }

        @Nested
        class 올바른_회원정보가_주어지면 {

            @Test
            void 토큰과_상태코드200을_리턴한다() throws Exception {
                // given
                LoginDto loginDto = LoginDto.builder()
                        .userId("test")
                        .password("1234567890qwe!@#")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().string(containsString("accessToken")));
            }
        }

        @Nested
        class 유효하지_않은_회원정보가_주어지면 {

            @Test
            void 아이디나_패스워드가_틀리면_500을_리턴한다() throws Exception {
                // given
                LoginDto loginDto = LoginDto.builder()
                        .userId("test")
                        .password("11111111test!@#")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isInternalServerError());
            }

            @Test
            void 아이디가_빈값이면_400을_리턴한다() throws Exception {
                // given
                LoginDto loginDto = LoginDto.builder()
                        .userId("")
                        .password("1234567890qwe!@#")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
            }

            @Test
            void 패스워드가_빈값이면_400을_리턴한다() throws Exception {
                // given
                LoginDto loginDto = LoginDto.builder()
                        .userId("test")
                        .password("")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
            }

        }
    }

}