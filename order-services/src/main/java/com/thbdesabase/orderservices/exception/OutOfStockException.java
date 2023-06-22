package com.thbdesabase.orderservices.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutOfStockException extends  RuntimeException {

    private Integer responseCode;
    private String responseMessages;

    public OutOfStockException(String message, Integer responseCode) {
        super(message);
        this.responseCode = responseCode;
        this.responseMessages = "item is out of stock";
    }
}
