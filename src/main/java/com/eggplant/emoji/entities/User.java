package com.eggplant.emoji.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class User extends Auditable<String> {
    public static final int DEFAULT_MEMBER_ID = 100100100;
    public static final String DEFAULT_FIRST_NAME = "John";
    public static final String DEFAULT_LAST_NAME = "Doe";
    public static final String DEFAULT_EMAIL = "john.doe@carleton.ca";

    public enum Role {
        STUDENT,
        PROFESSER,
        COORDINATOR;
    }

    public enum Program {
        BIOMEDICAL_ELECTRICAL,
        COMMUNICATION,
        COMPUTER_SYSTEMS,
        SOFTWARE;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // If the User role is STUDENT, then the memberId holds the student ID
    // If the User role is PROFESSOR or COORDINATOR, then the memberId holds the employee ID
    @NotNull
    private int memberId;
    @NotNull
    private Role role;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Pattern(regexp = "\\S+@(cmail\\.)?carleton.ca", message = "Email must be a valid email belonging to the (cmail.)carleton.ca domain.")
    private String email;
    @NotNull
    private Program program;
    @ManyToOne(cascade = CascadeType.ALL)
    private Project project;

    public User() {
        this(DEFAULT_MEMBER_ID, Role.STUDENT, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, Program.SOFTWARE);
    }

    public User(int memberId, Role role, String firstName, String lastName, String email, Program program) {
        this.memberId = memberId;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.program = program;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public int getMemberId() { return this.memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public Role getRole() { return this.role; }
    public void setRole(Role role) { this.role = role; }

    public String getFirstName() { return this.firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return this.lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public Program getProgram() { return this.program; }
    public void setProgram(Program program) { this.program = program; }

    public Project getProject() { return this.project; }
    public void setProject(Project project) { this.project = project; }

    @Override
    public String toString() {
        return this.role.name() + " " + String.valueOf(this.memberId) + " " + this.firstName + " " + this.lastName + " " + this.email;
    }
}
