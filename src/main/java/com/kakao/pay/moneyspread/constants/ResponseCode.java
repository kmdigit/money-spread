package com.kakao.pay.moneyspread.constants;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUC_RESPONSE("0x00", "응답 성공"),
    ERR_REQUEST("0x10","요청 파라메터 에러");

    private final String code;
    private final String msg;

    ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
