package com.eggplant.emoji.entities;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.eggplant.emoji.entities.User.Program;

@Entity
public class Project extends Auditable<String> {
    public static final String DEFAULT_PROJECT_NAME = "Dummy Project";
    public static final String DEFAULT_PROJECT_DESCRIPTION = "Dummy Project Description";
    public static final int MINIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT = 2;
    public static final int MAXIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String projectName;
    @NotNull
    private String description;
    @NotNull
    @Min(MINIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT)
    @Max(MAXIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT)
    private int minNumberOfStudents;
    @NotNull
    @Min(MINIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT)
    @Max(MAXIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT)
    private int maxNumberOfStudents;
    //Todo: Change ProgramRestrictions to use entities or @enumerated because the EnumSet doesn't work currently
//    @NotNull
    private EnumSet<Program> programRestrictions;
    @NotNull
    @OneToMany(mappedBy="project", cascade = CascadeType.ALL)
    private List<User> students;


    public Project() {
        this(DEFAULT_PROJECT_NAME, DEFAULT_PROJECT_DESCRIPTION, MINIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT, MAXIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT, EnumSet.allOf(Program.class));
    }

    public Project(String projectName, String description, int minNumberOfStudents, int maxNumberOfStudents, EnumSet<Program> programRestrictions) {
        this.projectName = projectName;
        this.description = description;
        this.minNumberOfStudents = minNumberOfStudents;
        this.maxNumberOfStudents = maxNumberOfStudents;
//        this.programRestrictions = programRestrictions;
        this.students = new ArrayList<User>();
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getProjectName() { return this.projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public int getMinNumberOfStudents() { return this.minNumberOfStudents; }
    public void setMinNumberOfStudents(int minNumberOfStudents) { this.minNumberOfStudents = minNumberOfStudents; }

    public int getMaxNumberOfStudents() { return this.maxNumberOfStudents; }
    public void setMaxNumberOfStudents(int maxNumberOfStudents) { this.maxNumberOfStudents = maxNumberOfStudents; }

    public List<User> getStudents() { return this.students; }
    public void setStudents(List<User> students) { this.students = students; }

    public EnumSet<Program> getProgramRestrictions() { return this.programRestrictions; }
    public void setProgramRestrictions(EnumSet<Program> programRestrictions) { this.programRestrictions = programRestrictions; }

    public boolean addStudent(User student) {
        if (this.students.contains(student)) {
            return false;
        }
        if (student.getRole() != User.Role.STUDENT) {
            return false;
        }
        if (this.students.size() == this.maxNumberOfStudents) {
            return false;
        }
        if (!this.programRestrictions.contains(student.getProgram())) {
            return false;
        }
        this.students.add(student);
        return true;
    }

    public boolean removeStudent(User student) {
        if (this.students.contains(student)) {
            this.students.remove(student);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
