package com.example.deliveryagent.rider.infra;

import com.example.deliveryagent.rider.domain.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Long> {
}
