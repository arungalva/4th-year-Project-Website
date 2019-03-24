package com.eggplant.emoji.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class LoginForm {

    @NotNull(message = "Email cannot be empty")
    @Pattern(regexp = "\\S+@(cmail\\.)?carleton.ca", message = "Email must be a valid email belonging to the (cmail.)carleton.ca domain.")
    @Column(unique = true)
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "Password cannot be empty")
//    @Min(value = 8)
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String toString() {
        return "LoginForm(Email: " + this.email + ", Password: " + this.password + ")";
    }
}
