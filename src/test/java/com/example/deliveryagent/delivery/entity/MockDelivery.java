package com.example.deliveryagent.delivery.entity;

import com.example.deliveryagent.delivery.domain.Delivery;
import com.example.deliveryagent.delivery.domain.DeliveryStatus;
import com.example.deliveryagent.member.entity.MockMember;

import java.time.LocalDateTime;

public class MockDelivery {

    public static Delivery create(DeliveryStatus status) {
        return Delivery.builder()
                .id(1L)
                .member(MockMember.create())
                .deliveryNo("test-123")
                .regTime(LocalDateTime.of(2022, 02, 21, 12, 10))
                .status(status)
                .deliveryStartTime(LocalDateTime.of(2022, 02, 21, 12, 15))
                .deliveryEndTime(LocalDateTime.of(2022, 02, 21, 12, 20))
                .build();
    }
}
