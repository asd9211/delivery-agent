package com.example.deliveryagent.delivery.presentation;

import com.example.deliveryagent.delivery.application.DeliveryService;
import com.example.deliveryagent.delivery.presentation.dto.request.*;
import com.example.deliveryagent.delivery.presentation.dto.response.DeliveryCreateResponse;
import com.example.deliveryagent.delivery.presentation.dto.response.DeliveryResponse;
import com.example.deliveryagent.delivery.presentation.dto.response.DeliveryUpdateResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;


    /**
     * 배달을 조회합니다.
     *
     * @param start 조회 시작일
     * @param end   조회 종료일
     * @return 배달정보 리스트
     */
    @ApiOperation(value = "배달 조회", notes = "날짜에 속하는 배달을 조회합니다.")
    @GetMapping
    public List<DeliveryResponse> findAllByRegDate(@Param("start") String start ,
                                                   @Param("end") String end) {
        return deliveryService.findByRegDate(start, end)
                .stream()
                .map(DeliveryResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 신규 배달을 등록합니다.
     *
     * @return 신규 등록된 배달 정보
     */
    @ApiOperation(value = "배달 등록", notes = "신규 배달 요청을 등록합니다.")
    @PostMapping
    public DeliveryCreateResponse create(@RequestBody @Validated DeliveryCreateDto deliveryCreateDto) {
        return new DeliveryCreateResponse(deliveryService.create(deliveryCreateDto));
    }

    /**
     * 배달 정보를 변경합니다.
     *
     * @return 변경된 배달 정보
     */
    @ApiOperation(value = "배달 변경", notes = "기존 배달 정보를 변경합니다.")
    @PutMapping("/{deliveryId}")
    public DeliveryUpdateResponse update(@PathVariable Long deliveryId,
                                         @RequestBody DeliveryUpdateDto deliveryUpdateDto) {
        return new DeliveryUpdateResponse(deliveryService.update(deliveryId, deliveryUpdateDto));
    }

    /**
     * 배달 기사를 변경합니다.
     *
     * @return 변경된 배달 정보
     */
    @ApiOperation(value = "배달기사 변경", notes = "기존 배달 정보의 배달기사를 변경합니다.")
    @PutMapping("/{deliveryId}/rider")
    public DeliveryUpdateResponse updateDeliveryRider(@PathVariable Long deliveryId,
                                                      @RequestBody @Validated DeliveryUpdateRiderDto deliveryUpdateRiderDto) {
        return new DeliveryUpdateResponse(deliveryService.updateDeliveryRider(deliveryId, deliveryUpdateRiderDto));
    }

    /**
     * 주소를 변경합니다.
     *
     * @return 변경된 배달 정보
     */
    @ApiOperation(value = "주소 변경", notes = "기존 배달 정보의 주소를 변경합니다.")
    @PutMapping("/{deliveryId}/address")
    public DeliveryUpdateResponse updateAddress(@PathVariable Long deliveryId,
                                                @RequestBody @Validated DeliveryUpdateAddressDto deliveryUpdateAddressDto) {
        return new DeliveryUpdateResponse(deliveryService.updateAddress(deliveryId, deliveryUpdateAddressDto));
    }

    /**
     * 배달 상태를 변경합니다.
     *
     * @return 변경된 배달 정보
     */
    @ApiOperation(value = "상태 변경", notes = "기존 배달 정보의 상태를 변경합니다.")
    @PutMapping("/{deliveryId}/status")
    public DeliveryUpdateResponse updateStatus(@PathVariable Long deliveryId,
                                               @RequestBody @Validated DeliveryUpdateStatusDto deliveryUpdateStatusDto) {
        return new DeliveryUpdateResponse(deliveryService.updateStatus(deliveryId, deliveryUpdateStatusDto));
    }

}
