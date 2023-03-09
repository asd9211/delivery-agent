package com.example.deliveryagent.delivery.application;

import com.example.deliveryagent.common.domain.MemberContext;
import com.example.deliveryagent.delivery.domain.Delivery;
import com.example.deliveryagent.delivery.domain.DeliveryStatus;
import com.example.deliveryagent.delivery.entity.MockDelivery;
import com.example.deliveryagent.delivery.exception.AddressImmutableException;
import com.example.deliveryagent.delivery.exception.AlreadyRiderExistsException;
import com.example.deliveryagent.delivery.exception.DateOutOfRangeException;
import com.example.deliveryagent.delivery.exception.DeliveryStatusNotFoundException;
import com.example.deliveryagent.delivery.infra.DeliveryRepository;
import com.example.deliveryagent.delivery.presentation.dto.request.*;
import com.example.deliveryagent.member.domain.Member;
import com.example.deliveryagent.member.entity.MockMember;
import com.example.deliveryagent.rider.application.RiderService;
import com.example.deliveryagent.rider.domain.Rider;
import com.example.deliveryagent.rider.entity.MockRider;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DisplayName("배달 서비스 테스트")
class DeliveryServiceTest {

    @InjectMocks
    private DeliveryService deliveryService;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private RiderService riderService;

    @BeforeEach
    void setUser() {
        Member member = MockMember.create();
        MemberContext.setCurrentMember(member);
    }

    @AfterEach
    void clearUser() {
        MemberContext.clear();
    }


    @Nested
    class 배달_조회_테스트 {
        @Test
        void 배달정보_조회_성공_날짜조건() {
            // given
            Delivery delivery = MockDelivery.create(DeliveryStatus.COMPLETED);
            String start = "20230220";
            String end = "20230223";

            doReturn(Arrays.asList(delivery)).when(deliveryRepository).findAllByMemberAndRegTimeBetween(any(Member.class), any(LocalDateTime.class), any(LocalDateTime.class));

            // when
            List<Delivery> deliveryList = deliveryService.findByRegDate(start, end);

            //then
            Assertions.assertTrue(deliveryList.size() > 0);
        }

        @Test
        void 배달정보_조회_실패_날짜범위초과() {
            // given
            String start = "20230220";
            String end = "20230225";

            // when
            DateOutOfRangeException e = assertThrows(DateOutOfRangeException.class, () -> deliveryService.findByRegDate(start, end));

            //then
            String expectedMessage = "조회할 수 있는 최대 범위는 3일입니다.";
            Assertions.assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Nested
    class 배달_등록_테스트 {
        @Test
        void 신규_배달_등록_성공() {
            // given
            DeliveryCreateDto deliveryCreateDto = DeliveryCreateDto.builder()
                    .address("동작구 어쩌구 저쩌동")
                    .build();

            doReturn(MockDelivery.create(DeliveryStatus.WAITING)).when(deliveryRepository).save(any(Delivery.class));

            // when
            Delivery delivery = deliveryService.create(deliveryCreateDto);

            // then
            Assertions.assertTrue(delivery.getId() != null);
        }
    }

    @Nested
    class 배달_변경_테스트 {
        @Test
        void 배달_기사_변경_성공() {
            // given
            Long deliveryId = 1L;
            DeliveryUpdateRiderDto deliveryUpdateDto = DeliveryUpdateRiderDto.builder()
                    .riderId(1L)
                    .build();

            Delivery delivery = MockDelivery.create(DeliveryStatus.WAITING);
            Rider rider = MockRider.create();

            doReturn(rider).when(riderService).findById(any(Long.class));
            doReturn(Optional.of(delivery)).when(deliveryRepository).findById(any(Long.class));

            // when
            deliveryService.updateDeliveryRider(deliveryId, deliveryUpdateDto);

            // then
            Assertions.assertTrue(rider == delivery.getRider());
        }

        @Test
        void 배달_기사_변경_실패_이미_배달기사가_확정된_경우() {
            // given
            Long deliveryId = 1L;
            DeliveryUpdateRiderDto deliveryUpdateDto = DeliveryUpdateRiderDto.builder()
                    .riderId(1L)
                    .build();

            Delivery delivery = MockDelivery.create(DeliveryStatus.ASSIGNED);
            Rider rider = MockRider.create();

            doReturn(rider).when(riderService).findById(any(Long.class));
            doReturn(Optional.of(delivery)).when(deliveryRepository).findById(any(Long.class));

            // when
            AlreadyRiderExistsException e = assertThrows(AlreadyRiderExistsException.class, () -> deliveryService.updateDeliveryRider(deliveryId, deliveryUpdateDto));

            // then
            String expectedMessage = "해당 배달에 이미 배달기사가 지정되었습니다.";
            Assertions.assertEquals(expectedMessage, e.getMessage());
        }

        @Test
        void 배달_주소_변경_성공() {
            // given
            String changedAddress = "관악구 어쩌고 저쩌동";
            Long deliveryId = 1L;
            DeliveryUpdateAddressDto deliveryUpdateDto = DeliveryUpdateAddressDto.builder()
                    .address(changedAddress)
                    .build();

            Delivery delivery = MockDelivery.create(DeliveryStatus.WAITING);
            doReturn(Optional.of(delivery)).when(deliveryRepository).findById(any(Long.class));

            // when
            deliveryService.updateAddress(deliveryId, deliveryUpdateDto);

            // then
            Assertions.assertEquals(changedAddress, delivery.getAddress());
        }

        @Test
        void 배달_주소_변경_실패_이미_배달기사가_확정된_경우() {
            // given
            String changedAddress = "관악구 어쩌고 저쩌동";
            Long deliveryId = 1L;
            DeliveryUpdateAddressDto deliveryUpdateDto = DeliveryUpdateAddressDto.builder()
                    .address(changedAddress)
                    .build();

            Delivery delivery = MockDelivery.create(DeliveryStatus.ASSIGNED);
            doReturn(Optional.of(delivery)).when(deliveryRepository).findById(any(Long.class));

            // when
            AddressImmutableException e = assertThrows(AddressImmutableException.class, () -> deliveryService.updateAddress(deliveryId, deliveryUpdateDto));

            // then
            String expectedMessage = "배달기사가 확정되어 목적지 변경이 불가능합니다.";
            Assertions.assertEquals(expectedMessage, e.getMessage());
        }

        @Test
        void 배달_상태_변경_성공() {
            // given
            String changedStatusCode = DeliveryStatus.STARTED.getCode();
            Long deliveryId = 1L;
            DeliveryUpdateStatusDto deliveryUpdateDto = DeliveryUpdateStatusDto.builder()
                    .statusCode(changedStatusCode)
                    .build();

            Delivery delivery = MockDelivery.create(DeliveryStatus.WAITING);
            doReturn(Optional.of(delivery)).when(deliveryRepository).findById(any(Long.class));

            // when
            deliveryService.updateStatus(deliveryId, deliveryUpdateDto);

            // then
            Assertions.assertEquals(delivery.getStatus().getCode(), changedStatusCode);
        }

        @Test
        void 배달_상태_변경_실패_존재하지않는_코드로_변경시() {
            // given
            String changedStatusCode = "999";
            Long deliveryId = 1L;
            DeliveryUpdateStatusDto deliveryUpdateDto = DeliveryUpdateStatusDto.builder()
                    .statusCode(changedStatusCode)
                    .build();

            Delivery delivery = MockDelivery.create(DeliveryStatus.WAITING);
            doReturn(Optional.of(delivery)).when(deliveryRepository).findById(any(Long.class));

            // when
            DeliveryStatusNotFoundException e = assertThrows(DeliveryStatusNotFoundException.class, () -> deliveryService.updateStatus(deliveryId, deliveryUpdateDto));

            //then
            String expectedMessage = "일치하는 배달상태가 없습니다.";
            Assertions.assertEquals(expectedMessage, e.getMessage());
        }
    }


}