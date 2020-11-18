package com.kakao.pay.moneyspread.models;

import com.kakao.pay.moneyspread.constants.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private String code;
    private String msg;
    T body;
}
