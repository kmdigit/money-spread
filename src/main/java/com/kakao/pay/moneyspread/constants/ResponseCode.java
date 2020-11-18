package com.kakao.pay.moneyspread.constants;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUC_RESPONSE("0x00", "응답 성공"),
    ERR_NOTUSER("0x41", "사용자 접근 권한 제한"),
    ERR_EXPIRED("0x42", "시간 초과"),
    ERR_DUPUSER("0x43", "중복 유저 제한"),
    ERR_NOTFOUND("0x44", "방 검색 실패");

    private final String code;
    private final String msg;

    ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
