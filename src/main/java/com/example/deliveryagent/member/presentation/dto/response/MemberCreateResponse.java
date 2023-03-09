package com.example.deliveryagent.member.presentation.dto.response;

import com.example.deliveryagent.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MemberCreateResponse {

    @JsonIgnore
    private Member member;

    public MemberCreateResponse(Member member) {
        this.member = member;
    }

    public Long getId() {
        return member.getId();
    }

    public String getUserId() {
        return member.getUserId();
    }

    public String getName() {
        return member.getName();
    }

}

