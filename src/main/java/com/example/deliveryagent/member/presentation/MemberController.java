package com.example.deliveryagent.member.presentation;

import com.example.deliveryagent.member.application.MemberService;
import com.example.deliveryagent.member.presentation.dto.request.LoginDto;
import com.example.deliveryagent.member.presentation.dto.request.MemberCreateDto;
import com.example.deliveryagent.member.presentation.dto.response.MemberCreateResponse;
import com.example.deliveryagent.member.presentation.dto.response.MemberResponse;
import com.example.deliveryagent.util.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원을 조회합니다.
     *
     * @param memberId 회원 식별자
     * @return 회원정보
     */
    @ApiOperation(value = "회원 조회", notes = "회원정보를 조회합니다.")
    @GetMapping("/{memberId}")
    public MemberResponse findById(@PathVariable final Long memberId) {
        return new MemberResponse(memberService.findById(memberId));
    }

    /**
     * 회원을 등록합니다.
     *
     * @param memberCreateDto 회원가입할 회원 정보
     * @return 회원가입 성공여부
     */
    @ApiOperation(value = "회원 가입", notes = "신규 회원을 등록합니다.")
    @PostMapping
    public MemberCreateResponse create(@RequestBody @Validated MemberCreateDto memberCreateDto) {
        return new MemberCreateResponse(memberService.create(memberCreateDto));
    }

    /**
     * 로그인을 합니다.
     *
     * @param loginDto 로그인할 회원 정보
     * @return access 토큰
     */
    @ApiOperation(value = "로그인", notes = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Validated LoginDto loginDto) {
        memberService.login(loginDto);
        String accessToken  = jwtTokenProvider.createToken(loginDto.getUserId());
        return new ResponseEntity<>(Map.of("accessToken", accessToken), HttpStatus.OK);
    }

}
