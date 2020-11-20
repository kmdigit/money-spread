package com.kakao.pay.moneyspread.exceptions;

public class DuplicateUserException extends ValidException {
    public DuplicateUserException() {
        super("중복된 사용자입니다");
    }
}
