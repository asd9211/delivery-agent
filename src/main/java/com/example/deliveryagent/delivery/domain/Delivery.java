package com.example.deliveryagent.delivery.domain;

import com.example.deliveryagent.delivery.exception.AddressImmutableException;
import com.example.deliveryagent.delivery.exception.AlreadyRiderExistsException;
import com.example.deliveryagent.delivery.exception.DeliveryStatusNotFoundException;
import com.example.deliveryagent.member.domain.Member;
import com.example.deliveryagent.rider.domain.Rider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "deliveries")
public class Delivery {

    /**
     * 기본키
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 배달 번호
     */
    @Column(nullable = false, length = 30)
    private String deliveryNo;
    /**
     * 배달 주소
     */
    @Column(nullable = false, length = 300)
    private String address;

    /**
     * 등록일
     */
    @Column
    private LocalDateTime regTime;

    /**
     * 배달 시작시간
     */
    @Column
    private LocalDateTime deliveryStartTime;

    /**
     * 배달 종료시간
     */
    @Column
    private LocalDateTime deliveryEndTime;

    /**
     * 배달 상태
     */
    @Column(nullable = false, length = 15)
    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus status;

    /**
     * 회원 정보
     */
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 배달 기사
     */
    @OneToOne
    @JoinColumn(name = "rider_id")
    private Rider rider;

    /**
     * 배달 정보를 변경합니다.
     */
    public void update(Delivery delivery) {
        if (isUpdatable()) {
            updateAddress(delivery.getAddress());
            updateRider(delivery.getRider());
            updateStatus(delivery.getStatus());
        }
    }

    /**
     * 배달 기사를 변경합니다.
     */
    public void updateRider(Rider rider) {
        if (isUpdatable()) {
            updateStatus(DeliveryStatus.ASSIGNED);
            this.rider = rider;
        } else {
            throw new AlreadyRiderExistsException();
        }
    }

    /**
     * 배달 주소를 변경합니다.
     */
    public void updateAddress(String address) {
        if (isUpdatable()) {
            this.address = address;
        } else {
            throw new AddressImmutableException();
        }
    }

    /**
     * 배달 시작시간을 변경합니다.
     */
    private void updateStartTime(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            this.deliveryStartTime = localDateTime;
        }
    }

    /**
     * 배달 종료시간을 변경합니다.
     */
    private void updateEndTime(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            this.deliveryEndTime = localDateTime;
        }
    }

    /**
     * 배달 상태를 변경합니다.
     */
    public void updateStatus(DeliveryStatus status) {
        switch (status) {
            case WAITING:
                break;
            case ASSIGNED:
                break;
            case STARTED:
                updateStartTime(LocalDateTime.now());
                break;
            case COMPLETED:
                updateEndTime(LocalDateTime.now());
                break;
            case CANCELLED:
                break;
            default:
                throw new DeliveryStatusNotFoundException();
        }

        this.status = status;
    }

    public String getUserId() {
        return this.member.getUserId();
    }

    public String getRiderUserId() {
        return isAssignRider() ? this.rider.getUserId() : "";
    }

    public String getStatusCode(){
        return this.status.getCode();
    }

    private boolean isUpdatable() {
        return this.status.isWaiting();
    }

    private boolean isAssignRider(){
        return this.rider != null;
    }

}
