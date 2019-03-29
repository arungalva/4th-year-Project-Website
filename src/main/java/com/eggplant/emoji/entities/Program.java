package com.eggplant.emoji.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "program")
public class Program {
//    BIOMEDICAL_ELECTRICAL("Biomedical Electrical", "BE"),
//    COMMUNICATION_ENGINEERING("Communication Engineering", "CE"),
//    COMPUTER_SYSTEMS_ENGINEERING("Computer Systems Engineering", "CSE"),
//    SOFTWARE_ENGINEERING("Software Engineering", "SE"),
//    ELECTRICAL_ENGINEERING("Electrical Engineering", "EE");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String programName;

    @NotNull
    private String programCode;

    Program(String programName, String programCode) {
        this.programName = programName;
        this.programCode = programCode;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public String getProgramCode() { return programCode; }
    public void setProgramCode(String programCode) { this.programCode = programCode; }



}
