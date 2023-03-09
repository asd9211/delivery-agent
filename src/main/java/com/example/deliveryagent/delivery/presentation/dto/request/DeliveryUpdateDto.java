package com.example.deliveryagent.delivery.presentation.dto.request;

import com.example.deliveryagent.delivery.domain.Delivery;
import com.example.deliveryagent.delivery.domain.DeliveryStatus;
import com.example.deliveryagent.rider.domain.Rider;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DeliveryUpdateDto {

    @ApiModelProperty(value = "배달기사 식별자", required = true, example = "1")
    private Long riderId;
    @ApiModelProperty(value = "주소", required = true, example = "서울시 동작구 어쩌동")
    private String address;
    @ApiModelProperty(value = "상태코드", required = true, example = "2")
    private String statusCode;

    public Delivery toEntity(Rider rider) {
        return Delivery.builder()
                .rider(rider)
                .status(DeliveryStatus.findByStatusCode(statusCode))
                .address(address)
                .build();
    }

}
