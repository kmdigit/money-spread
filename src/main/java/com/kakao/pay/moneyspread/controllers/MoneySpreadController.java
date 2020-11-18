package com.kakao.pay.moneyspread.controllers;

import com.kakao.pay.moneyspread.constants.Header;
import com.kakao.pay.moneyspread.constants.ResponseCode;
import com.kakao.pay.moneyspread.entities.RecvUser;
import com.kakao.pay.moneyspread.entities.SpreadRoom;
import com.kakao.pay.moneyspread.models.ApiRequest;
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

    @PostMapping(value = "/seed", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> seed(@RequestHeader(Header.USER_ID) long userId,
                                    @RequestHeader(Header.ROOM_ID) String roomId,
                                    @RequestBody SpreadSeed spreadSeed) {
        final String token = moneySpreadService.createRoom(roomId, userId, spreadSeed);
        return ApiResponse.<String>builder()
                .code(ResponseCode.SUC_RESPONSE.getCode())
                .msg(ResponseCode.SUC_RESPONSE.getMsg())
                .body(token)
                .build();
    }

    @PostMapping("/digup/{token}")
    public void digUp(@RequestHeader(Header.USER_ID) long userId,
                      @RequestHeader(Header.ROOM_ID) String roomId,
                      @RequestBody SpreadSeed spreadSeed) {

    }

    @GetMapping("/retrieve/{token}")
    public ApiResponse<ApiRequest.SpreadRetrieve> retrieve(@RequestHeader(Header.USER_ID) long userId,
                                                           @RequestHeader(Header.ROOM_ID) String roomId,
                                                           @PathVariable String token) {
        SpreadRoom spreadRoom = moneySpreadService.findRoom(token, userId);

        ApiRequest.SpreadRetrieve spreadRetrieve = new ApiRequest.SpreadRetrieve(
                spreadRoom.getCreatedTime(),
                spreadRoom.getSeedMoney(),
                spreadRoom.getRecvUsers().stream().mapToLong(RecvUser::getRecvMoney).sum());
        spreadRoom.getRecvUsers().forEach(u -> spreadRetrieve.addDigger(u.getUserId(), u.getRecvMoney()));

        return ApiResponse.<ApiRequest.SpreadRetrieve>builder()
                .code(ResponseCode.SUC_RESPONSE.getCode())
                .msg(ResponseCode.SUC_RESPONSE.getMsg())
                .body(spreadRetrieve)
                .build();
    }
}