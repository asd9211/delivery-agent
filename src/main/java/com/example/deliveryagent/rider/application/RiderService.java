package com.example.deliveryagent.rider.application;

import com.example.deliveryagent.common.domain.MemberContext;
import com.example.deliveryagent.rider.domain.Rider;
import com.example.deliveryagent.rider.exception.RiderNotFoundException;
import com.example.deliveryagent.rider.infra.RiderRepository;
import com.example.deliveryagent.rider.presentation.dto.request.RiderCreateDto;
import com.example.deliveryagent.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RiderService {

    private final RiderRepository riderRepository;

    /**
     * 배달기사를 조회합니다.
     *
     * @param riderId 배달기사 식벽자
     * @return 배달기사 정보
     */
    public Rider findById(Long riderId) {
        return riderRepository.findById(riderId)
                .orElseThrow(RiderNotFoundException::new);
    }

    /**
     * 배달기사를 등록합니다.
     *
     * @param riderCreateDto 배달기사 생성 정보
     * @return 생성된 배달기사 정보
     */
    public Rider create(RiderCreateDto riderCreateDto) {
        Member member = MemberContext.getCurrentMember();
        Rider entity = riderCreateDto.toEntity(member);
        return riderRepository.save(entity);
    }
}
