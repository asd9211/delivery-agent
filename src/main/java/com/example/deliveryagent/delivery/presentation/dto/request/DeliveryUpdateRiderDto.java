package com.example.deliveryagent.delivery.presentation.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DeliveryUpdateRiderDto {

    @ApiModelProperty(value = "배달기사 식별자", required = true, example = "1")
    @NotNull(message = "배달기사를 입력해주세요.")
    private Long riderId;

}
