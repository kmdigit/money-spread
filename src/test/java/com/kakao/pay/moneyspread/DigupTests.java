package com.kakao.pay.moneyspread;

import com.kakao.pay.moneyspread.constants.ResponseCode;
import com.kakao.pay.moneyspread.entities.RecvUser;
import com.kakao.pay.moneyspread.entities.SpreadRoom;
import com.kakao.pay.moneyspread.repositories.SpreadRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("받기 API 테스트")

public class DigupTests extends MoneySpreadTest {
    @MockBean
    private SpreadRoomRepository repository;

    final String requestURI = "/kakao/pay/spread/digup/";

    @Test
    @DisplayName("정상 받기")
    void successRecvMoney() throws Exception {
        // given
        when(repository.findByToken(anyString())).thenReturn(stub());

        // when
        ResultActions result = digUp("abc", 13L, "ABC");

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ResponseCode.SUC_RESPONSE.getCode())))
                .andExpect(jsonPath("$.body", instanceOf(Integer.class)));
    }

    @Test
    @DisplayName("토큰 및 방 아이디 다름")
    void notSameRoomId() throws Exception {
        when(repository.findByToken(anyString())).thenReturn(stub());

        ResultActions result = digUp("abc", 13L, "ABD");

        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(ResponseCode.ERR_VALID.getCode())))
                .andExpect(jsonPath("$.body", nullValue()));
    }

    @Test
    @DisplayName("받기 시간 초과")
    void expiredRecvTime() throws Exception {
        final SpreadRoom spreadRoom = new SpreadRoom("abc", 1L, "ABC", 10000L, 10000L,5);
        spreadRoom.setCreatedTime(LocalDateTime.now().minusMinutes(20));
        spreadRoom.addUser(new RecvUser(11L, 2200));
        spreadRoom.addUser(new RecvUser(12L, 3100));
        when(repository.findByToken(anyString())).thenReturn(spreadRoom);

        ResultActions result = digUp("abc", 13L, "ABD");

        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(ResponseCode.ERR_VALID.getCode())))
                .andExpect(jsonPath("$.body", nullValue()));
    }

    @Test
    @DisplayName("자기 자신은 못받음")
    void selfRecv() throws Exception {
        when(repository.findByToken(anyString())).thenReturn(stub());

        ResultActions result = digUp("abc", 1L, "ABC");

        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(ResponseCode.ERR_VALID.getCode())))
                .andExpect(jsonPath("$.body", nullValue()));
    }

    @Test
    @DisplayName("한 번 받은 사람은 못받음")
    void duplicateRecv() throws Exception {
        when(repository.findByToken(anyString())).thenReturn(stub());

        ResultActions result = digUp("abc", 12L, "ABC");

        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(ResponseCode.ERR_VALID.getCode())))
                .andExpect(jsonPath("$.body", nullValue()));
    }

    @Test
    @DisplayName("남은 사람이 없음")
    void leftMoney() throws Exception {
        final SpreadRoom spreadRoom = new SpreadRoom("abc", 1L, "ABC", 10000L, 0L,5);
        spreadRoom.setCreatedTime(LocalDateTime.now());
        spreadRoom.addUser(new RecvUser(11L, 2000));
        spreadRoom.addUser(new RecvUser(12L, 2000));
        spreadRoom.addUser(new RecvUser(13L, 2000));
        spreadRoom.addUser(new RecvUser(14L, 2000));
        spreadRoom.addUser(new RecvUser(15L, 2000));
        when(repository.findByToken(anyString())).thenReturn(spreadRoom);

        ResultActions result = digUp("abc", 16L, "ABC");

        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(ResponseCode.ERR_VALID.getCode())))
                .andExpect(jsonPath("$.body", nullValue()));
    }
}
