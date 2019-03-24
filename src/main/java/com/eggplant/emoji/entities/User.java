package com.eggplant.emoji.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users")
public class User extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // If the User role is STUDENT, then the memberId holds the student ID
    // If the User role is PROFESSOR or COORDINATOR, then the memberId holds the employee ID
    @NotNull
    @Min(value = 100000000, message = "Number must be a 9 digit number")
    @Max(value = 999999999, message = "Number must be a 9 digit number")
    private int memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    @NotNull(message = "Role cannot be empty")
    private Role role;

    @NotNull
    @NotEmpty(message = "First name can't be empty")
    private String firstName;

    @NotNull
    @NotEmpty(message = "Last name can't be empty")
    private String lastName;

    @NotNull
    @Pattern(regexp = "\\S+@(cmail\\.)?carleton.ca", message = "Email must be a valid email belonging to the (cmail.)carleton.ca domain.")
    @Column(unique = true)
    private String email;

    @NotNull
    @Min(8)
    @NotEmpty(message = "Password cannot be empty")
    private String password;

//    @NotNull
//    @Column(name = "user_program")
//    @Enumerated(EnumType.STRING)
//    private Program program;

    @ManyToOne(cascade = CascadeType.ALL)
    private Project project;

    public User(){}

    public User(int memberId,String firstName, String lastName, String email, Role role, Program program) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
//        this.program = program;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public int getMemberId() { return this.memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public String getFirstName() { return this.firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return this.lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public Project getProject() { return this.project; }
    public void setProject(Project project) { this.project = project; }

    @Override
    public String toString() {
        return this.memberId + " " + this.firstName + " " + this.lastName + " " + this.email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(String role) {
       this.role = Role.valueOf(role.toUpperCase());
    }

//    public Program getProgram() { return program; }
//
//    public void setProgram(String program) {
//        this.program = Program.valueOf(program);
//    }
}
