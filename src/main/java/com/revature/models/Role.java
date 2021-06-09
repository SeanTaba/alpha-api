package com.revature.models;

public enum Role {
    ADMIN(5),
    DEV(4),
    PREMIUM_USER(3),
    BASIC_USER(2),
    LOCKED(1),
    UNAUTHORIZED(0);

    private int permissionLevel;

    Role(int permissionLevel){
        this.permissionLevel = permissionLevel;
    }
}
