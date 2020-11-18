package com.kakao.pay.moneyspread;

import com.kakao.pay.moneyspread.constants.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public abstract class MoneySpreadTest {
    @Autowired
    MockMvc mockMvc;

    protected ResultActions retrieve(final String token, final long userId, final String roomId) throws Exception {
        return mockMvc.perform(get("/kakao/pay/spread/retrieve/" + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(Header.USER_ID, userId)
                .header(Header.ROOM_ID, roomId)
        ).andDo(print());
    }
}
