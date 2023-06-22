package com.thbdesabase.orderservices.enumeration;


import lombok.Getter;

@Getter
public enum ERole {

    USER("User"),
    ADMIN("Admin"),
    MANAGEMENT("Management");

    private String name;

    ERole(String name) {
        this.name = name;
    }
}
