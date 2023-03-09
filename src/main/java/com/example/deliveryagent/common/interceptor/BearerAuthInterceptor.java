package com.example.deliveryagent.common.interceptor;

import com.example.deliveryagent.common.domain.MemberContext;
import com.example.deliveryagent.common.exception.TokenInvalidException;
import com.example.deliveryagent.common.exception.TokenNotFoundException;
import com.example.deliveryagent.member.application.MemberService;
import com.example.deliveryagent.member.domain.Member;
import com.example.deliveryagent.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class BearerAuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberService memberService;

    /**
     * 접근 토큰을 검사합니다.
     *
     * @param request  http요청 정보
     * @param response http응답 정보
     * @return 통과여부
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {

        String token = request.getHeader("Authorization");
        String tokenType = "Bearer ";

        if (ObjectUtils.isEmpty(token) || !token.startsWith(tokenType)) {
            throw new TokenNotFoundException();
        }

        token = token.substring(tokenType.length());

        if (!jwtTokenProvider.validateToken(token)) {
            throw new TokenInvalidException();
        }

        setMemberContext(token);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MemberContext.clear();
    }

    /**
     * 회원의 정보를 현재 Thread에 저장합니다.
     *
     * @param token 접근 토큰 정보
     */
    private void setMemberContext(String token) {
        String userId = jwtTokenProvider.getUserId(token);
        Member loginMember = memberService.findByUserId(userId);
        MemberContext.setCurrentMember(loginMember);
    }
}
