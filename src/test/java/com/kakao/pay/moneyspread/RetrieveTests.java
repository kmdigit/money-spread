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
        final SpreadRoom spreadRoom = new SpreadRoom("abc", 1L, "ABC", 10000L, 5);
        spreadRoom.setCreatedTime(LocalDateTime.now());
        spreadRoom.addUser(new RecvUser(11L, 300));
        spreadRoom.addUser(new RecvUser(12L, 400));
        when(repository.findByToken("abc")).thenReturn(spreadRoom);

        // when
        ResultActions result = retrieve("abc", 1L, "ABC");

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ResponseCode.SUC_RESPONSE.getCode())))
                .andExpect(jsonPath("$.msg", is(ResponseCode.SUC_RESPONSE.getMsg())))
                .andExpect(jsonPath("$.body.diggers.length()", is(2)));
    }
}
