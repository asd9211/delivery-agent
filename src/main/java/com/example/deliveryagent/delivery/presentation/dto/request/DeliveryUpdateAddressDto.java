package com.example.deliveryagent.delivery.presentation.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DeliveryUpdateAddressDto {

    @ApiModelProperty(value = "주소", required = true, example = "서울시 동작구 어쩌동")
    @NotBlank(message = "주소를 입력하세요")
    private String address;

}
