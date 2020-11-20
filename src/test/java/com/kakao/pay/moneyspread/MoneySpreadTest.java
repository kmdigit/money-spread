package com.kakao.pay.moneyspread;

import com.kakao.pay.moneyspread.constants.Header;
import com.kakao.pay.moneyspread.entities.RecvUser;
import com.kakao.pay.moneyspread.entities.SpreadRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public abstract class MoneySpreadTest {
    @Autowired
    MockMvc mockMvc;

    protected SpreadRoom stub() {
        final SpreadRoom spreadRoom = new SpreadRoom("abc", 1L, "ABC", 10000L, 10000L,5);
        spreadRoom.setCreatedTime(LocalDateTime.now());
        spreadRoom.addUser(new RecvUser(11L, 2200));
        spreadRoom.addUser(new RecvUser(12L, 3100));
        return spreadRoom;
    }

    protected ResultActions digUp(final String token, final long userId, final String roomId) throws Exception {
        return mockMvc.perform(
                post("/kakao/pay/spread/digup/" + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(Header.USER_ID, userId)
                        .header(Header.ROOM_ID, roomId)
        ).andDo(print());
    }

    protected ResultActions retrieve(final String token, final long userId, final String roomId) throws Exception {
        return mockMvc.perform(get("/kakao/pay/spread/retrieve/" + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(Header.USER_ID, userId)
                .header(Header.ROOM_ID, roomId)
        ).andDo(print());
    }
}
