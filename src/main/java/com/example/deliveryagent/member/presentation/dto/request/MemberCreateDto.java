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
public class MemberCreateDto {
    @ApiModelProperty(value = "아이디", required = true, example = "test")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;
    @ApiModelProperty(value = "비밀번호", required = true, example = "12345qwert!@")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
    @ApiModelProperty(value = "이름", required = true, example = "정대만")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .build();
    }
}
