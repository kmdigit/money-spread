package com.kakao.pay.moneyspread;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.pay.moneyspread.constants.Header;
import com.kakao.pay.moneyspread.constants.ResponseCode;
import com.kakao.pay.moneyspread.models.ApiRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("컨트롤러 테스트")
public class SeedTests extends MoneySpreadTest {
    @Test
    @DisplayName("뿌리기 API 테스트")
    void testSeeding() throws Exception {
        final ApiRequest.SpreadSeed spreadSeed = new ApiRequest.SpreadSeed();
        spreadSeed.setSeedMoney(1000);
        spreadSeed.setUserCount(5);

        final long userId = 123;
        final String roomId = "funlee";

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(
                post("/kakao/pay/spread/seeding")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(Header.USER_ID, userId)
                        .header(Header.ROOM_ID, roomId)
                        .content(mapper.writeValueAsString(spreadSeed)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body", hasLength(3)))
                .andExpect(jsonPath("$.code", is(ResponseCode.SUC_RESPONSE.getCode())));
    }
}
