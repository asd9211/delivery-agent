package com.example.deliveryagent.rider.presentation;

import com.example.deliveryagent.rider.application.RiderService;
import com.example.deliveryagent.rider.presentation.dto.request.RiderCreateDto;
import com.example.deliveryagent.rider.presentation.dto.response.RiderCreateResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rider")
public class RiderController {

    private final RiderService riderService;

    /**
     * 배달기사를 등록합니다.
     *
     * @return 생성된 배달기사 정보
     */
    @ApiOperation(value = "배달기사 등록", notes = "신규 배달기사를 등록합니다.")
    @PostMapping
    public RiderCreateResponse create(@RequestBody RiderCreateDto riderCreateDto) {
        return new RiderCreateResponse(riderService.create(riderCreateDto));
    }
}
