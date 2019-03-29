package com.eggplant.emoji.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "program")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String programName;

    @NotNull
    private String programCode;

    @ManyToMany(mappedBy = "programRestrictions")
    private Collection<Project> projects;

    public Program(){this("Default","Default");}

    public Program(String programName, String programCode) {
        this.programName = programName;
        this.programCode = programCode;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public String getProgramCode() { return programCode; }
    public void setProgramCode(String programCode) { this.programCode = programCode; }

    public Collection<Project> getProjects() { return projects; }
    public void setProjects(Collection<Project> projects) { this.projects = projects; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Program program = (Program) obj;
        return Objects.equals(id, program.id) &&
                Objects.equals(programName, program.programName) &&
                Objects.equals(programCode, program.programCode);
    }
}
