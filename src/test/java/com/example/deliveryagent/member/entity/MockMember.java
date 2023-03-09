package com.example.deliveryagent.member.entity;

import com.example.deliveryagent.member.domain.Member;

public class MockMember {

    public static Member create() {
        return Member.builder()
                .id(1L)
                .name("정대만")
                .userId("test")
                .password("1234567890qwe!@#")
                .build();
    }
}
