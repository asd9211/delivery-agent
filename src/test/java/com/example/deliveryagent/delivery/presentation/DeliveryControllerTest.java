package com.example.deliveryagent.delivery.presentation;

import com.example.deliveryagent.common.domain.MemberContext;
import com.example.deliveryagent.delivery.application.DeliveryService;
import com.example.deliveryagent.delivery.domain.Delivery;
import com.example.deliveryagent.delivery.domain.DeliveryStatus;
import com.example.deliveryagent.delivery.presentation.dto.request.*;
import com.example.deliveryagent.member.application.MemberService;
import com.example.deliveryagent.member.domain.Member;
import com.example.deliveryagent.member.presentation.dto.request.MemberCreateDto;
import com.example.deliveryagent.rider.application.RiderService;
import com.example.deliveryagent.rider.domain.Rider;
import com.example.deliveryagent.rider.presentation.dto.request.RiderCreateDto;
import com.example.deliveryagent.util.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("배달 API 테스트")
class DeliveryControllerTest {

    private static String apiUrl = "/delivery";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RiderService riderService;

    private Delivery savedDelivery;

    private Rider savedRider;

    @BeforeEach
    void setData() {
        setUser();
        setRider();
        setDelivery();
    }

    void setUser() {
        Member member = memberService.create(테스트_유저_생성_정보());
        MemberContext.setCurrentMember(member);
    }

    void setRider() {
        savedRider = riderService.create(배달기사_생성_정보());
    }

    void setDelivery() {
        savedDelivery = deliveryService.create(배달_생성_정보());
    }

    @Nested
    class 배달_조회_API는 {

        @Nested
        class 올바른_날짜조건이_주어지면 {

            @Test
            void 배달_리스트와_상태코드200을_리턴한다() throws Exception {
                // given
                String start = "20220223";
                String end = "20220225";
                String queryString = "?start=" + start + "&end=" + end;

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(apiUrl + queryString));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isOk());
            }
        }

        @Nested
        class 초과한_날짜조건이_주어지면 {

            @Test
            void 상태코드500을_리턴한다() throws Exception {
                // given
                String start = "20220223";
                String end = "20220227";
                String queryString = "?start=" + start + "&end=" + end;

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(apiUrl + queryString));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isInternalServerError());
            }
        }
    }

    @Nested
    class 배달_등록_API는 {

        @Nested
        class 올바른_배달정보가_주어지면 {

            @Test
            void 배달정보를_저장하고_배달정보와_200을_리턴한다() throws Exception {
                // given
                DeliveryCreateDto deliveryCreateDto = DeliveryCreateDto.builder()
                        .address("서울시 동작구 무슨동")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryCreateDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().string(containsString("deliveryNo")));
            }
        }

        @Nested
        class 유효하지_않은_배달정보가_주어지면 {

            @Test
            void 주소가_공백일_경우_상태코드400을_리턴한다() throws Exception {
                // given
                DeliveryCreateDto deliveryCreateDto = DeliveryCreateDto.builder()
                        .address("")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryCreateDto)));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
            }
        }
    }

    @Nested
    class 배달_변경_API는 {

        @Nested
        class 올바른_배달정보가_주어지면 {

            @Test
            void 배달정보를_변경하고_배달정보와_200을_리턴한다() throws Exception {
                // given
                DeliveryUpdateDto deliveryUpdateDto = DeliveryUpdateDto.builder()
                        .riderId(savedRider.getId())
                        .address("서울시 구로구 무슨동")
                        .statusCode("2")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(apiUrl + "/" + savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryUpdateDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().string(containsString("deliveryNo")));
            }
        }

        @Nested
        class 유효하지_않은_배달정보가_주어지면 {

            @Test
            void 존재하지_않는_배달기사가_주어진다면_상태코드500을_리턴한다() throws Exception {
                // given
                DeliveryUpdateDto deliveryUpdateDto = DeliveryUpdateDto.builder()
                        .riderId(Long.MAX_VALUE)
                        .address("서울시 구로구 무슨동")
                        .statusCode("2")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(apiUrl + "/" + savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryUpdateDto)));


                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isInternalServerError());
            }
        }

        @Nested
        class 배달_대기_상태가_아니면 {
            @Test
            void 상태코드500을_리턴한다() throws Exception {
                // given
                DeliveryUpdateDto deliveryUpdateDto = DeliveryUpdateDto.builder()
                        .riderId(Long.MAX_VALUE)
                        .address("서울시 구로구 무슨동")
                        .statusCode("2")
                        .build();

                deliveryService.updateStatus(savedDelivery.getId(), 배달_수정_정보_출발_상태());

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(apiUrl + "/" + savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryUpdateDto)));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isInternalServerError());
            }
        }
    }

    @Nested
    class 배달_주소_변경_API는 {

        @Nested
        class 올바른_배달정보가_주어지면 {

            @Test
            void 배달정보를_변경하고_배달정보와_200을_리턴한다() throws Exception {
                // given
                DeliveryUpdateAddressDto deliveryUpdateAddressDto = DeliveryUpdateAddressDto.builder()
                        .address("서울시 구로구 무슨동")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(apiUrl + "/" + savedDelivery.getId() + "/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryUpdateAddressDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().string(containsString("deliveryNo")));
            }
        }

        @Nested
        class 유효하지_않은_배달정보가_주어지면 {

            @Test
            void 주소가_공백일_경우_상태코드400을_리턴한다() throws Exception {
                // given
                DeliveryUpdateAddressDto deliveryUpdateAddressDto = DeliveryUpdateAddressDto.builder()
                        .address("")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(apiUrl + "/" + savedDelivery.getId() + "/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryUpdateAddressDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
            }
        }

        @Nested
        class 배달_대기_상태가_아니면 {

            @Test
            void 상태코드500을_리턴한다() throws Exception {
                // given
                DeliveryUpdateAddressDto deliveryUpdateAddressDto = DeliveryUpdateAddressDto.builder()
                        .address("서울시 구로구 무슨동")
                        .build();

                deliveryService.updateStatus(savedDelivery.getId(), 배달_수정_정보_출발_상태());

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(apiUrl + "/" + savedDelivery.getId() + "/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryUpdateAddressDto)));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isInternalServerError());
            }
        }
    }


    @Nested
    class 배달_배달기사_변경_API는 {

        @Nested
        class 올바른_배달정보가_주어지면 {

            @Test
            void 배달정보를_변경하고_배달정보와_200을_리턴한다() throws Exception {
                // given
                DeliveryUpdateRiderDto deliveryUpdateAddressDto = DeliveryUpdateRiderDto.builder()
                        .riderId(savedRider.getId())
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(apiUrl + "/" + savedDelivery.getId() + "/rider")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryUpdateAddressDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().string(containsString("deliveryNo")));
            }
        }

        @Nested
        class 유효하지_않은_배달정보가_주어지면 {

            @Test
            void 배달기사가_null일_경우_상태코드400을_리턴한다() throws Exception {
                // given
                DeliveryUpdateRiderDto deliveryUpdateAddressDto = DeliveryUpdateRiderDto.builder()
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(apiUrl + "/" + savedDelivery.getId() + "/rider")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryUpdateAddressDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
            }

            @Test
            void 존재하지_않는_배달기사가_주어진다면_상태코드500을_리턴한다() throws Exception {
                // given
                DeliveryUpdateRiderDto deliveryUpdateRiderDto = DeliveryUpdateRiderDto.builder()
                        .riderId(Long.MAX_VALUE)
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(apiUrl + "/" + savedDelivery.getId() + "/rider")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryUpdateRiderDto)));


                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isInternalServerError());
            }
        }

        @Nested
        class 배달_대기_상태가_아니면 {

            @Test
            void 상태코드500을_리턴한다() throws Exception {
                // given
                DeliveryUpdateRiderDto deliveryUpdateAddressDto = DeliveryUpdateRiderDto.builder()
                        .riderId(savedRider.getId())
                        .build();


                deliveryService.updateStatus(savedDelivery.getId(), 배달_수정_정보_출발_상태());

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(apiUrl + "/" + savedDelivery.getId() + "/rider")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryUpdateAddressDto)));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isInternalServerError());
            }
        }
    }


    @Nested
    class 배달_상태_변경_API는 {

        @Nested
        class 올바른_배달정보가_주어지면 {

            @Test
            void 배달정보를_변경하고_배달정보와_200을_리턴한다() throws Exception {
                // given
                DeliveryUpdateStatusDto deliveryUpdateStatusDto = DeliveryUpdateStatusDto.builder()
                        .statusCode(DeliveryStatus.ASSIGNED.getCode())
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(apiUrl + "/" + savedDelivery.getId() + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryUpdateStatusDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().string(containsString("deliveryNo")));
            }
        }

        @Nested
        class 유효하지_않은_배달정보가_주어지면 {

            @Test
            void 배달상태가_빈값일_경우_상태코드400을_리턴한다() throws Exception {
                // given
                DeliveryUpdateStatusDto deliveryUpdateStatusDto = DeliveryUpdateStatusDto.builder()
                        .statusCode("")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(apiUrl + "/" + savedDelivery.getId() + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryUpdateStatusDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
            }

            @Test
            void 존재하지_않는_상태코드가_주어진다면_상태코드500을_리턴한다() throws Exception {
                // given
                DeliveryUpdateStatusDto deliveryUpdateStatusDto = DeliveryUpdateStatusDto.builder()
                        .statusCode("999")
                        .build();

                // when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(apiUrl + "/" + savedDelivery.getId() + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deliveryUpdateStatusDto)));

                // then
                resultActions
                        .andExpect(MockMvcResultMatchers.status().isInternalServerError());
            }
        }
    }


    MemberCreateDto 테스트_유저_생성_정보() {
        return MemberCreateDto.builder()
                .name("정대만")
                .userId("test")
                .password("1234567890qwe!@#")
                .build();
    }

    RiderCreateDto 배달기사_생성_정보() {
        return RiderCreateDto.builder()
                .licenseNo("11-1234-5678")
                .licensePlate("123")
                .build();
    }

    DeliveryCreateDto 배달_생성_정보() {
        return DeliveryCreateDto.builder()
                .address("서울시 동작구 무슨동")
                .build();
    }

    DeliveryUpdateStatusDto 배달_수정_정보_출발_상태() {
        return DeliveryUpdateStatusDto.builder()
                .statusCode(DeliveryStatus.STARTED.getCode())
                .build();
    }

}
