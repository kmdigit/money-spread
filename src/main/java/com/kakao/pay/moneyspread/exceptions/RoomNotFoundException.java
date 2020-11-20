package com.kakao.pay.moneyspread.exceptions;

public class RoomNotFoundException extends NotFoundException {
    public RoomNotFoundException() {
        super("방을 찾을 수 없습니다.");
    }
}
