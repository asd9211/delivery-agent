package com.example.deliveryagent.delivery.infra;

import com.example.deliveryagent.delivery.domain.Delivery;
import com.example.deliveryagent.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findAllByMemberAndRegTimeBetween(Member member, LocalDateTime start, LocalDateTime end);
}
