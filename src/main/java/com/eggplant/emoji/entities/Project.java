package com.eggplant.emoji.entities;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.eggplant.emoji.entities.User.Program;

@Entity
public class Project {
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
    @NotNull
    private EnumSet<Program> programRestrictions;
    @NotNull
    @OneToMany(mappedBy="project", cascade = CascadeType.ALL)
    private List<User> students;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Column(name = "modified_date")
    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;
    @JoinColumn(name = "created_by")
    @CreatedBy
    private User createdBy;
    @JoinColumn(name = "modified_by")
    @LastModifiedBy
    private User modifiedBy;

    public Project() {
        this(DEFAULT_PROJECT_NAME, DEFAULT_PROJECT_DESCRIPTION, MINIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT, MAXIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT, EnumSet.allOf(Program.class));
    }

    public Project(String projectName, String description, int minNumberOfStudents, int maxNumberOfStudents, EnumSet<Program> programRestrictions) {
        this.projectName = projectName;
        this.description = description;
        this.minNumberOfStudents = minNumberOfStudents;
        this.maxNumberOfStudents = maxNumberOfStudents;
        this.programRestrictions = programRestrictions;
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

    @Override
    public String toString() {
        return this.description;
    }
}
