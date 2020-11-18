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
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiResponse notFound(NotFoundException e) {
        return ApiResponse.builder().code(ResponseCode.ERR_NOTFOUND.getCode()).msg(e.getMessage()).build();
    }

    @ExceptionHandler(ValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiResponse valid(ValidException e) {
        return ApiResponse.builder().code(ResponseCode.ERR_VALID.getCode()).msg(e.getMessage()).build();
    }
}
