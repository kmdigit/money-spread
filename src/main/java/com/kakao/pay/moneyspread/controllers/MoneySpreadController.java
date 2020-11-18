package com.kakao.pay.moneyspread.controllers;

import com.kakao.pay.moneyspread.constants.Header;
import com.kakao.pay.moneyspread.constants.ResponseCode;
import com.kakao.pay.moneyspread.models.ApiResponse;
import com.kakao.pay.moneyspread.models.SpreadSeed;
import com.kakao.pay.moneyspread.services.MoneySpreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/kakao/pay/spread")
public class MoneySpreadController {
    private final MoneySpreadService moneySpreadService;

    @PostMapping(value = "/seeding", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> seeding(
            @RequestHeader(Header.USER_ID) long userId,
            @RequestHeader(Header.ROOM_ID) String roomId,
            @RequestBody SpreadSeed spreadSeed) {
        final String token = moneySpreadService.createRoom(roomId, userId, spreadSeed);
        return ApiResponse.<String>builder()
                .code(ResponseCode.SUC_RESPONSE.getCode())
                .msg(ResponseCode.SUC_RESPONSE.getMsg())
                .body(token)
                .build();
    }
}