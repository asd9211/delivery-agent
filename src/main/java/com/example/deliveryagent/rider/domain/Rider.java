package com.example.deliveryagent.rider.domain;

import com.example.deliveryagent.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "riders")
public class Rider {

    /**
     * 기본키
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 운전면허번호
     */
    @Column(nullable = false, length = 30)
    private String licenseNo;

    /**
     * 원동기번호
     */
    @Column(nullable = false, length = 30)
    private String licensePlate;

    /**
     * 회원 정보
     */
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public String getUserId(){
        return this.member.getUserId();
    }

}
