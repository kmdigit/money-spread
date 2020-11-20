package com.kakao.pay.moneyspread.services;

import com.kakao.pay.moneyspread.entities.SpreadRoom;
import com.kakao.pay.moneyspread.models.ApiDTO;

public interface MoneySpreadService {
    /**
     * 뿌리기 방 생성 함수이다.
     * 방은 무조건 생성되어야 하며 token은 중복되지 말아야 한다.
     *
     * @param roomId 방 식별 키
     * @param userId 방 생성 유저 식별 키
     * @param spreadSeed 방 생성 시 초기 뿌릴 금액
     * @return 방 접근 token
     */
    String createRoom(final String roomId, final long userId, final ApiDTO.SpreadSeed spreadSeed);

    /**
     * 수집 함수이다.
     * 같은 사용자가 또 가져가거나 방을 만든 사람이 가져가서는 안된다.
     * 10분 지난 방에서는 수집되면 안된다.
     *
     * @param token 뿌리기 방 접근 token
     * @param userId 수집할 유저 식별 키
     * @param roomId 접근할 방 식별 키
     * @return 수집 한 금액
     */
    long digUp(final String token, final long userId, final String roomId);

    /**
     * 뿌리기 방 조회함수이다.
     * 방을 만든 사람만 조회할 수 있으며 7일이 지난 방은 조회가 불가능하다.
     *
     * @param token 방 접근 token
     * @param userId 방 접근 유저 식별 키
     * @return 뿌리기 방 정보
     */
    SpreadRoom findRoom(final String token, final long userId);
}
