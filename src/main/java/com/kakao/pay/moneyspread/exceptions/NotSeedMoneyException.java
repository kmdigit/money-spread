package com.kakao.pay.moneyspread.exceptions;

public class NotSeedMoneyException extends ValidException {
    public NotSeedMoneyException() {
        super("돈 뿌리기는 마감 됐습니다");
    }
}
