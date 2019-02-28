package com.eggplant.emoji.entities;

import javax.persistence.*;

@Entity
public class User {
    private static final Int DEFAULT_MEMBER_ID = 100100100;
    private static final String DEFAULT_FIRST_NAME = 'John';
    private static final String DEFAULT_LAST_NAME = 'Doe';
    private static final String DEFAULT_EMAIL = 'john.doe@carleton.ca';

    enum Role {
        STUDENT,
        PROFESSER,
        COORDINATOR
    }

    private Long id;
    private Int member_id;
    private Role role;
    private String first_name;
    private String last_name;
    private String email;

    public User() {
        this(DEFAULT_MEMBER_ID, Role.STUDENT, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL);
    }

    public User(Int member_id, Role role, String first_name, String last_name, String email) {
        this.member_id = member_id;
        this.role = role;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    @Id
    @GeneratedValue
    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    public Int getMemberId() { return this.member_id; }

    public void setMemberId(Int member_id) { this.member_id = member_id; }

    public Role getRole() { return this.role; }

    public void setRole(Role role) { this.role = role; }

    public String getFirstName() { return this.first_name; }

    public void setFirstName(String first_name) { this.first_name = first_name; }

    public String getLastName() { return this.last_name; }

    public void setLastName(String last_name) { this.last_name = last_name; }

    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return this.role.name() + " " + this.first_name + " " + this.last_name + " " + String.valueOf(this.member_id) + " " + this.email;
    }
}