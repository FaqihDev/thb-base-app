package com.thbdesabase.orderservices.common;

public enum ResponseCode {

    SUCCESS("00"),
    FAILED("01");

    private String code;

    public String getCode() {
        return code;
    }

    ResponseCode(String code) {
        this.code = code;
    }
}
