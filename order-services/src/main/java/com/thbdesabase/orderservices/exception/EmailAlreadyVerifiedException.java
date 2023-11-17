package com.thbdesabase.orderservices.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailAlreadyVerifiedException extends RuntimeException{

    private Integer responseCode;
    private String exceptionMessage;

}
