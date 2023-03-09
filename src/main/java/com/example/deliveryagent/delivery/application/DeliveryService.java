package com.example.deliveryagent.delivery.application;

import com.example.deliveryagent.common.domain.MemberContext;
import com.example.deliveryagent.delivery.domain.Delivery;
import com.example.deliveryagent.delivery.domain.DeliveryStatus;
import com.example.deliveryagent.delivery.exception.DateOutOfRangeException;
import com.example.deliveryagent.delivery.exception.DeliveryNotFoundException;
import com.example.deliveryagent.delivery.infra.DeliveryRepository;
import com.example.deliveryagent.delivery.presentation.dto.request.*;
import com.example.deliveryagent.member.domain.Member;
import com.example.deliveryagent.rider.application.RiderService;
import com.example.deliveryagent.rider.domain.Rider;
import com.example.deliveryagent.util.DateParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private final RiderService riderService;

    /**
     * 배달정보를 조회합니다.
     *
     * @param deliveryId 배달 식별자
     * @return 배달정보
     */
    public Delivery findById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(DeliveryNotFoundException::new);
    }

    /**
     * 배달정보를 조회합니다.
     *
     * @param start 조회 시작일
     * @param end   조회 종료일
     * @return 배달정보 리스트
     */
    public List<Delivery> findByRegDate(String start, String end) {
        Member member = MemberContext.getCurrentMember();
        LocalDateTime startDateTime = DateParser.parseStartTime(start);
        LocalDateTime endDateTime = DateParser.parseEndTime(end);
        validateDateRange(startDateTime, endDateTime);
        return deliveryRepository.findAllByMemberAndRegTimeBetween(member, startDateTime, endDateTime);
    }

    /**
     * 신규 배달을 등록합니다.
     *
     * @param deliveryCreateDto 배달 생성 정보
     * @return 배달 식별자
     */
    public Delivery create(DeliveryCreateDto deliveryCreateDto) {
        Member member = MemberContext.getCurrentMember();
        Delivery delivery = deliveryCreateDto.toEntity(member);
        return deliveryRepository.save(delivery);
    }

    /**
     * 배달 정보를 변경합니다.
     *
     * @param deliveryId        배달 식별자
     * @param deliveryUpdateDto 배달 생성 정보
     * @return 변경된 배달 정보
     */
    public Delivery update(Long deliveryId, DeliveryUpdateDto deliveryUpdateDto) {
        Long riderId = deliveryUpdateDto.getRiderId();
        Rider rider = riderService.findById(riderId);
        Delivery delivery = findById(deliveryId);
        Delivery updatedDelivery = deliveryUpdateDto.toEntity(rider);
        delivery.update(updatedDelivery);
        return delivery;
    }

    /**
     * 배달 기사를 변경합니다.
     *
     * @param deliveryId        배달 식별자
     * @param deliveryUpdateRiderDto 배달 수정 정보
     * @return 변경된 배달 정보
     */
    public Delivery updateDeliveryRider(Long deliveryId, DeliveryUpdateRiderDto deliveryUpdateRiderDto) {
        Long riderId = deliveryUpdateRiderDto.getRiderId();
        Rider rider = riderService.findById(riderId);
        Delivery delivery = findById(deliveryId);
        delivery.updateRider(rider);
        return delivery;
    }

    /**
     * 주소를 변경합니다.
     *
     * @param deliveryId        배달 식별자
     * @param deliveryUpdateAddressDto 배달 수정 정보
     * @return 변경된 배달 정보
     */
    public Delivery updateAddress(Long deliveryId, DeliveryUpdateAddressDto deliveryUpdateAddressDto) {
        String changedAddress = deliveryUpdateAddressDto.getAddress();
        Delivery delivery = findById(deliveryId);
        delivery.updateAddress(changedAddress);
        return delivery;
    }

    /**
     * 배달 상태를 변경합니다.
     *
     * @param deliveryId        배달 식별자
     * @param deliveryUpdateStatusDto 배달 수정 정보
     * @return 변경된 배달 정보
     */
    public Delivery updateStatus(Long deliveryId, DeliveryUpdateStatusDto deliveryUpdateStatusDto) {
        String statusCode = deliveryUpdateStatusDto.getStatusCode();
        Delivery delivery = findById(deliveryId);
        DeliveryStatus status = DeliveryStatus.findByStatusCode(statusCode);
        delivery.updateStatus(status);
        return delivery;
    }

    /**
     * 조회 범위를 검사합니다.
     *
     * @param startDateTime 시작일
     * @param endDateTime   종료일
     */
    private void validateDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int maxBetweenDays = 3;
        Duration duration = Duration.between(startDateTime, endDateTime);
        if (duration.toDays() > maxBetweenDays) {
            throw new DateOutOfRangeException();
        }
    }

}
