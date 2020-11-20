package com.kakao.pay.moneyspread.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * API 응답 공통 클래스
 */
@Getter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private String code;
    private String msg;
    T body;
}
