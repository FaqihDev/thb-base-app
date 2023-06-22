package com.thbdesabase.orderservices.enumeration;

import lombok.Getter;

@Getter
public enum EPreferredSambungTime {

    PAGI("Pagi"),
    SIANG("Siang"),
    MALAM("Malam");

    private String name;

    EPreferredSambungTime(String name) {
        this.name = name;
    }
}
