package com.example.deliveryagent.rider.presentation.dto.response;

import com.example.deliveryagent.rider.domain.Rider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RiderResponse {

    @JsonIgnore
    private Rider rider;

    public RiderResponse(Rider rider) {
        this.rider = rider;
    }

    public String getLicenseNo() {
        return rider.getLicenseNo();
    }

    public String getLicensePlate() {
        return rider.getLicensePlate();
    }

    public String getUserId() {
        return rider.getUserId();
    }


}

