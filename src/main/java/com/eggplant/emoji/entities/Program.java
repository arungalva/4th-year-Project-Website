package com.eggplant.emoji.entities;

public enum Program {
    BIOMEDICAL_ELECTRICAL("Biomedical Electrical", "BE"),
    COMMUNICATION_ENGINEERING("Communication Engineering", "CE"),
    COMPUTER_SYSTEMS_ENGINEERING("Computer Systems Engineering", "CSE"),
    SOFTWARE_ENGINEERING("Software Engineering", "SE"),
    ELECTRICAL_ENGINEERING("Electrical Engineering", "EE");

    private String programName;
    private String programCode;

    Program(String programName, String programCode) {
        this.programName = programName;
        this.programCode = programCode;
    }

    public String getProgramName() { return programName; }

    public String getProgramCode() { return programCode; }
}
