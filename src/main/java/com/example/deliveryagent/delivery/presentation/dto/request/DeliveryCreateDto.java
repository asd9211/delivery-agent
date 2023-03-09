package com.example.deliveryagent.delivery.presentation.dto.request;

import com.example.deliveryagent.delivery.domain.Delivery;
import com.example.deliveryagent.delivery.domain.DeliveryStatus;
import com.example.deliveryagent.member.domain.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DeliveryCreateDto {

    @ApiModelProperty(value = "주소", required = true, example = "서울시 동작구 어쩌동")
    @NotBlank(message = "주소를 입력하세요")
    private String address;

    public String getAddress() {
        return address;
    }

    public Delivery toEntity(Member member) {
        return Delivery.builder()
                .member(member)
                .deliveryNo(member.getUserId() + "-" + System.currentTimeMillis())
                .status(DeliveryStatus.WAITING)
                .address(address)
                .regTime(LocalDateTime.now())
                .build();
    }

}
