package com.kakao.pay.moneyspread;

import com.kakao.pay.moneyspread.constants.Header;
import com.kakao.pay.moneyspread.constants.ResponseCode;
import com.kakao.pay.moneyspread.entities.RecvUser;
import com.kakao.pay.moneyspread.entities.SpreadRoom;
import com.kakao.pay.moneyspread.repositories.SpreadRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("조회 API 테스트")
public class RetrieveTests extends MoneySpreadTest {
    @MockBean
    private SpreadRoomRepository repository;

    @Test
    @DisplayName("정상 조회")
    void successTest() throws Exception {
        // given
        when(repository.findByToken("abc")).thenReturn(stub());

        // when
        ResultActions result = retrieve("abc", 1L, "ABC");

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ResponseCode.SUC_RESPONSE.getCode())))
                .andExpect(jsonPath("$.msg", is(ResponseCode.SUC_RESPONSE.getMsg())))
                .andExpect(jsonPath("$.body.diggers.length()", is(2)));
    }

    @Test
    @DisplayName("다른 사용자 조회")
    void userValidTest() throws Exception {
        // given
        when(repository.findByToken("abc")).thenReturn(stub());

        // when
        ResultActions result = retrieve("abc", 2L, "ABC");

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(ResponseCode.ERR_VALID.getCode())));
    }

    @Test
    @DisplayName("다른 방 조회")
    void roomNotFoundTest() throws Exception {
        // given
        when(repository.findByToken("abc")).thenReturn(stub());

        // when
        ResultActions result = retrieve("ABC", 1L, "ABC");

        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(ResponseCode.ERR_NOTFOUND.getCode())));
    }
}
