package com.example.deliveryagent.member.presentation.dto.request;

import com.example.deliveryagent.member.domain.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LoginDto {
    @ApiModelProperty(value = "아이디", required = true, example = "test")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;
    @ApiModelProperty(value = "비밀번호", required = true, example = "12345qwert!@")
    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    public Member toEntity(){
        return Member.builder()
                .userId(userId)
                .password(password)
                .build();
    }
}
