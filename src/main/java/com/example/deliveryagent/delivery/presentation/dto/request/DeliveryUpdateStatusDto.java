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
public class DeliveryUpdateStatusDto {
    @ApiModelProperty(value = "상태코드", required = true, example = "2")
    @NotBlank(message = "상태코드를 입력해주세요.")
    private String statusCode;

}
