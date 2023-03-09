package com.example.deliveryagent.delivery.presentation.dto.response;

import com.example.deliveryagent.delivery.domain.Delivery;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class DeliveryResponse {

    @JsonIgnore
    private Delivery delivery;

    public DeliveryResponse(Delivery delivery) {
        this.delivery = delivery;
    }

    public String getDeliveryNo() {
        return delivery.getDeliveryNo();
    }

    public String getAddress() {
        return delivery.getAddress();
    }

    public LocalDateTime getRegTime() {
        return delivery.getRegTime();
    }

    public LocalDateTime getDeliveryStartTime() {
        return delivery.getDeliveryStartTime();
    }

    public LocalDateTime getDeliveryEndTime() {
        return delivery.getDeliveryEndTime();
    }

    public String getUserId() {
        return delivery.getUserId();
    }

    public String getRiderUserId() {
        return delivery.getRiderUserId();
    }

    public String getStatusCode() {
        return delivery.getStatusCode();
    }
}

