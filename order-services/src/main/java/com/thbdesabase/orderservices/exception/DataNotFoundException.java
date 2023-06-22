package com.thbdesabase.orderservices.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataNotFoundException extends RuntimeException{

    private Integer responseCode;
    private String responseMessages;


    public DataNotFoundException(Integer responseCode) {
        this.responseCode = responseCode;
    }

}
