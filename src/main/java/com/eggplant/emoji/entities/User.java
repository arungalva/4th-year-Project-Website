package com.eggplant.emoji.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class User {
    private static final int DEFAULT_MEMBER_ID = 100100100;
    private static final String DEFAULT_FIRST_NAME = "John";
    private static final String DEFAULT_LAST_NAME = "Doe";

    enum Role {
        STUDENT,
        PROFESSER,
        COORDINATOR;
    }

    enum Program {
        BIOMEDICAL_ELECTRICAL,
        COMMUNICATION,
        COMPUTER_SYSTEMS,
        SOFTWARE;
    }

    private Long id;
    // If the User role is STUDENT, then the member_id holds the student ID
    // If the User role is PROFESSOR or COORDINATOR, then the member_id holds the employee ID
    private int member_id;
    private Role role;
    private String first_name;
    private String last_name;
    private String email;
    private Program program;

    public User() {
        this(DEFAULT_MEMBER_ID, Role.STUDENT, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME);
    }

    public User(int member_id, Role role, String first_name, String last_name) {
        this.member_id = member_id;
        this.role = role;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    @NotNull
    public int getMemberId() { return this.member_id; }

    public void setMemberId(int member_id) { this.member_id = member_id; }

    @NotNull
    public Role getRole() { return this.role; }

    public void setRole(Role role) { this.role = role; }

    @NotNull
    public String getFirstName() { return this.first_name; }

    public void setFirstName(String first_name) { this.first_name = first_name; }

    @NotNull
    public String getLastName() { return this.last_name; }

    public void setLastName(String last_name) { this.last_name = last_name; }

    @NotNull
    @Pattern(regexp = "\\S+@(cmail\\.)?carleton.ca", message = "Email must be a valid email belonging to the (cmail.)carleton.ca domain.")
    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }

    public Program getProgram() { return this.program; }

    public void setProgram(Program program) { this.program = program; }

    @Override
    public String toString() {
        return this.role.name() + " " + this.first_name + " " + this.last_name + " " + String.valueOf(this.member_id);
    }
}
