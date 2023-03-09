package com.example.deliveryagent.rider.presentation.dto.request;

import com.example.deliveryagent.rider.domain.Rider;
import com.example.deliveryagent.member.domain.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RiderCreateDto {
    @ApiModelProperty(value = "운전면허 번호", required = true, example = "10-123456-890")
    private String licenseNo;

    @ApiModelProperty(value = "원동기 번호", required = true, example = "77가0123")
    private String licensePlate;

    public Rider toEntity(Member member){
        return Rider.builder()
                .licenseNo(licenseNo)
                .licensePlate(licensePlate)
                .member(member)
                .build();
    }
}
