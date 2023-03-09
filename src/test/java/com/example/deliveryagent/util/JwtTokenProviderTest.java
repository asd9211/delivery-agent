package com.example.deliveryagent.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class JwtTokenProviderTest {

    private String secretKey = "slamDunk";

    @Test
    void 토큰_생성_성공(){
        // given
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", secretKey);

        // when
        String token = jwtTokenProvider.createToken("test");

        // then
        Assertions.assertTrue(token.length() > 0);
    }

    @Test
    void 토큰_정보_조회_성공(){
        // given
        String userId = "정대만";
        String token = createToken(userId);
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", secretKey);

        // when
        String responseUserId = jwtTokenProvider.getUserId(token);

        // then
        Assertions.assertEquals(userId, responseUserId);
    }

    @Test
    void 토큰_유효성_검사_성공(){
        // given
        String userId = "정대만";
        String token = createToken(userId);
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", secretKey);

        // when
        boolean result = jwtTokenProvider.validateToken(token);

        // then
        Assertions.assertTrue(result);
    }


    private String createToken(String id){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", secretKey);
        return jwtTokenProvider.createToken(id);
    }
}