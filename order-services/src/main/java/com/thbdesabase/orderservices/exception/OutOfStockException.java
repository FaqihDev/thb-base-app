package com.thbdesabase.orderservices.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutOfStockException extends  RuntimeException {

    private Integer responseCode;
    private String exceptionMessage;

}
