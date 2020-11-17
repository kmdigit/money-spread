package com.kakao.pay.moneyspread.models;

import lombok.Getter;
import lombok.Setter;

/**
 * 뿌리기 API 요청 RequestBody 클래스
 */
@Getter @Setter
public class SpreadSeed {
    /**
     * 뿌릴 금액
     */
    private long seedMoney;
    /**
     * 뿌릴 인원
     */
    private int userCount;
}
