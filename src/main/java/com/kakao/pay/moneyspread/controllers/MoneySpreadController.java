package com.kakao.pay.moneyspread.controllers;

import com.kakao.pay.moneyspread.constants.Header;
import com.kakao.pay.moneyspread.models.SpreadSeed;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kakao/pay/spread")
public class MoneySpreadController {
    @PostMapping(value = "/seeding", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String seeding(
            @RequestHeader(Header.USER_ID) long userId,
            @RequestHeader(Header.ROOM_ID) String roomId,
            @RequestBody SpreadSeed spreadSeed) {
        return String.format("%d, %s", userId, roomId);
    }
}

