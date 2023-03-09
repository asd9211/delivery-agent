package com.example.deliveryagent.member.presentation.dto.response;

import com.example.deliveryagent.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Builder
@ToString
public class MemberResponse {

    @JsonIgnore
    private Member member;

    public MemberResponse(Member member){
        this.member = member;
    }

    public String getUserId() {
        return member.getUserId();
    }

    public String getName() {
        return member.getName();
    }

}

