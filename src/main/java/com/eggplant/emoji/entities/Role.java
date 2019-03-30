package com.eggplant.emoji.entities;

/**
 * Role enum used to represent the different roles for the users in the system
 */
public enum Role {

    STUDENT("STUDENT"),
    PROFESSOR("PROFESSOR"),
    COORDINATOR("COORDINATOR");

    private String value;

    Role(String val) {
        this.value = val;
    }
}
