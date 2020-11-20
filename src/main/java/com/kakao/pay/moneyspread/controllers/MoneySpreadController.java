package com.kakao.pay.moneyspread.controllers;

import com.kakao.pay.moneyspread.constants.Header;
import com.kakao.pay.moneyspread.constants.ResponseCode;
import com.kakao.pay.moneyspread.entities.RecvUser;
import com.kakao.pay.moneyspread.entities.SpreadRoom;
import com.kakao.pay.moneyspread.models.ApiDTO;
import com.kakao.pay.moneyspread.models.ApiResponse;
import com.kakao.pay.moneyspread.services.MoneySpreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/kakao/pay/spread")
public class MoneySpreadController {
    private final MoneySpreadService moneySpreadService;

    /**
     * 뿌리기 API
     *
     * @param userId 사용자 식별 키
     * @param roomId 뿌리기 방 식별 키
     * @param spreadSeed 뿌릴 금액
     * @return 생성 된 뿌리기 방 token
     */
    @PostMapping(value = "/seed", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> seed(@RequestHeader(Header.USER_ID) long userId,
                                    @RequestHeader(Header.ROOM_ID) String roomId,
                                    @RequestBody ApiDTO.SpreadSeed spreadSeed) {
        final String token = moneySpreadService.createRoom(roomId, userId, spreadSeed);
        return ApiResponse.<String>builder()
                .code(ResponseCode.SUC_RESPONSE.getCode())
                .msg(ResponseCode.SUC_RESPONSE.getMsg())
                .body(token)
                .build();
    }

    /**
     * 수집 API
     *
     * @param userId 사용자 식별 키
     * @param roomId 뿌리기 방 식별 키
     * @param token 접근할 뿌리기 방 token
     * @return 수집한 금액
     */
    @PostMapping("/digup/{token}")
    public ApiResponse<Long> digUp(@RequestHeader(Header.USER_ID) long userId,
                      @RequestHeader(Header.ROOM_ID) String roomId,
                      @PathVariable String token) {
        final long money = moneySpreadService.digUp(token, userId, roomId);
        return ApiResponse.<Long>builder()
                .code(ResponseCode.SUC_RESPONSE.getCode())
                .msg(ResponseCode.SUC_RESPONSE.getMsg())
                .body(money)
                .build();
    }

    /**
     * 조회 API
     *
     * @param userId 사용자 식별 키
     * @param roomId 뿌리기 방 식별 키
     * @param token 뿌리기 방 접근 token
     * @return 뿌리기 방 현재 정보
     * @see ApiDTO.SpreadRetrieve
     */
    @GetMapping("/retrieve/{token}")
    public ApiResponse<ApiDTO.SpreadRetrieve> retrieve(@RequestHeader(Header.USER_ID) long userId,
                                                       @RequestHeader(Header.ROOM_ID) String roomId,
                                                       @PathVariable String token) {
        SpreadRoom spreadRoom = moneySpreadService.findRoom(token, userId);

        ApiDTO.SpreadRetrieve spreadRetrieve = new ApiDTO.SpreadRetrieve(
                spreadRoom.getCreatedTime(),
                spreadRoom.getSeedMoney(),
                spreadRoom.getRecvUsers().stream().mapToLong(RecvUser::getRecvMoney).sum());
        spreadRoom.getRecvUsers().forEach(u -> spreadRetrieve.addDigger(u.getUserId(), u.getRecvMoney()));

        return ApiResponse.<ApiDTO.SpreadRetrieve>builder()
                .code(ResponseCode.SUC_RESPONSE.getCode())
                .msg(ResponseCode.SUC_RESPONSE.getMsg())
                .body(spreadRetrieve)
                .build();
    }
}