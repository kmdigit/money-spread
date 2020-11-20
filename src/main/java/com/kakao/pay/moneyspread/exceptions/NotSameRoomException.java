package com.kakao.pay.moneyspread.exceptions;

public class NotSameRoomException extends ValidException {
    public NotSameRoomException() {
        super("매칭 된 방 정보가 잘못 됐습니다");
    }
}
