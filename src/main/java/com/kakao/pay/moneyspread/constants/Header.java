package com.kakao.pay.moneyspread.constants;

/**
 * 요청한 사용자의 식별값은 숫자 형태이며 "X-USER-ID" 라는 HTTP Header로 전달됩니다.
 * 요청한 사용자가 속한 대화방의 식별값은 문자 형태이며 "X-ROOM-ID" 라는 HTTP Header로 전달됩니다.
 */
public class Header {
    public static final String USER_ID = "X-USER-ID";
    public static final String ROOM_ID = "X-ROOM-ID";
}
