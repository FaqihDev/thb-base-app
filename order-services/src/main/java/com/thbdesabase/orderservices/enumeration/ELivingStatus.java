package com.thbdesabase.orderservices.enumeration;

import lombok.Getter;

@Getter
public enum ELivingStatus {

    SEWA_TAHUNAN("Sewa Tahunan"),
    SEWA_BULANAN("Sewa Bulanan"),
    MENUMPANG("Menumpang"),
    RUMAH_SENDIRI("Rumah Sendiri");


    private String name;

    ELivingStatus(String name) {
        this.name = name;
    }
}
