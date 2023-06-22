package com.thbdesabase.orderservices.endpoint.handler;

import com.thbdesabase.orderservices.common.BaseResponse;
import com.thbdesabase.orderservices.exception.DataNotFoundException;
import com.thbdesabase.orderservices.exception.OutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceHandler {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse dataNotFoundException (DataNotFoundException e) {
        return new BaseResponse<>(e.getResponseMessages(),Integer.valueOf(e.getResponseCode()));
    }

    @ExceptionHandler(OutOfStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse outOfStockException(OutOfStockException e) {
        return new BaseResponse(e.getResponseMessages(), Integer.valueOf(e.getResponseCode()));
    }

}
