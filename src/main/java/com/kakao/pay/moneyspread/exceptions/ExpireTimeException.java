package com.kakao.pay.moneyspread.exceptions;

public class ExpireTimeException extends ValidException {
    public ExpireTimeException() {
        super("할당 된 시간이 지났습니다");
    }
}
