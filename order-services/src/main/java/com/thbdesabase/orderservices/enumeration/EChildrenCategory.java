package com.thbdesabase.orderservices.enumeration;

import lombok.Getter;

@Getter
public enum EChildrenCategory {
    PAUD("Paud"),
    CABERAWIT("Caberawit"),
    PRA_REMAJA("Pra Remaja"),
    REMAJA("Remaja"),
    PGM("PGM");

    private String name;


    EChildrenCategory(String name) {
        this.name = name;
    }


}
