package com.example.deliveryagent.member.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "members")
@ToString
public class Member {

    /**
     * 기본키
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자 아이디
     */
    @Column(nullable = false, length = 30, unique = true)
    private String userId;

    /**
     * 비밀번호
     */
    @Column(nullable = false, length = 64)
    private String password;

    /**
     * 이름
     */
    @Column(nullable = false, length = 50)
    private String name;

}
