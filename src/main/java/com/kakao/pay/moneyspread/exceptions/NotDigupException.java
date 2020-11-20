package com.kakao.pay.moneyspread.exceptions;

public class NotDigupException extends NotFoundException {
    public NotDigupException() {
        super("돈을 받을 수 없습니다.");
    }
}
