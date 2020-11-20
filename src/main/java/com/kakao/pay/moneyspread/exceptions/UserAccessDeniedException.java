package com.kakao.pay.moneyspread.exceptions;

public class UserAccessDeniedException extends ValidException {
    public UserAccessDeniedException() {
        super("사용자 접근 권한이 없습니다.");
    }
}
