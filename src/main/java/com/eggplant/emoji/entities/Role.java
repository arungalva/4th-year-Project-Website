package com.eggplant.emoji.entities;

/**
 * Role enum used to represent the different roles for the users in the system
 */
public enum Role {

    //Todo: Need to add COORDINATOR role as well
    STUDENT("STUDENT"),
    PROFESSOR("PROFESSOR");

    private String value;

    Role(String val) {
        this.value = val;
    }
}
