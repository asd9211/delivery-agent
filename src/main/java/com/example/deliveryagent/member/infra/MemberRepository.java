package com.example.deliveryagent.member.infra;

import com.example.deliveryagent.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByUserIdAndPassword(String userId, String password);
    boolean existsByUserId(String userId);

}
