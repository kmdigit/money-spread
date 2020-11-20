package com.kakao.pay.moneyspread.advises;

import com.kakao.pay.moneyspread.constants.ResponseCode;
import com.kakao.pay.moneyspread.exceptions.NotFoundException;
import com.kakao.pay.moneyspread.exceptions.ValidException;
import com.kakao.pay.moneyspread.models.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvise {
    /**
     * 뿌리기 방 토큰 검색 실패
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiResponse notFound(NotFoundException e) {
        return ApiResponse.builder().code(ResponseCode.ERR_NOTFOUND.getCode()).msg(e.getMessage()).build();
    }

    /**
     * 요청 검증 실패
     * - 토큰과 방정보가 다를 때
     * - 뿌린 사람이 수집하려고 할 때
     * - 수집한 사람이 다시 수집할 때
     * - 금액이 남아있지 않을 때
     */
    @ExceptionHandler(ValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiResponse valid(ValidException e) {
        return ApiResponse.builder().code(ResponseCode.ERR_VALID.getCode()).msg(e.getMessage()).build();
    }
}
