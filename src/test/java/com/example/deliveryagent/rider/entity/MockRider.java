package com.example.deliveryagent.rider.entity;

import com.example.deliveryagent.member.entity.MockMember;
import com.example.deliveryagent.rider.domain.Rider;

public class MockRider {

    public static Rider create() {
        return Rider.builder()
                .licenseNo("01-123123")
                .id(1L)
                .member(MockMember.create())
                .build();
    }
}
