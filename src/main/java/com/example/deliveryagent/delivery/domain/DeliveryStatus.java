package com.example.deliveryagent.delivery.domain;

import com.example.deliveryagent.delivery.exception.DeliveryStatusNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DeliveryStatus {
    WAITING("1"),    // 대기
    ASSIGNED("2"),   // 배달기사 확정
    STARTED("3"),    // 배달 출발
    COMPLETED("4"),  // 배달 완료
    CANCELLED("0");  // 배달 취소

    private String code;

    DeliveryStatus(String code) {
        this.code = code;
    }

    public boolean isWaiting() {
        return this == WAITING;
    }

    public boolean isAssigned() {
        return this == ASSIGNED;
    }

    public boolean isStarted() {
        return this == STARTED;
    }

    public boolean isCompleted() {
        return this == COMPLETED;
    }

    public boolean isCancelled() {
        return this == CANCELLED;
    }

    public static DeliveryStatus findByStatusCode(String code) {
        return Arrays.stream(DeliveryStatus.values())
                .filter(deliveryStatus -> deliveryStatus.getCode().equals(code))
                .findAny()
                .orElseThrow(DeliveryStatusNotFoundException::new);
    }

}
