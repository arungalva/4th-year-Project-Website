package com.eggplant.emoji.entities;

import java.util.Date;
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
    private static final int MINIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT = 2;
    private static final int MAXIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT = 5;

    private Long id;
    private String project_name;
    private String description;
    private int min_number_of_students;
    private int max_number_of_students;
    private List<User> students;
    private List<Program> program_restrictions;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Date createdDate;
    @Column(name = "modified_date")
    @LastModifiedDate
    private Date modifiedDate;
    @Column(name = "created_by")
    @CreatedBy
    private User createdBy;
    @Column(name = "modified_by")
    @LastModifiedBy
    private User modifiedBy;

    public Project() {
        this.students = new ArrayList<User>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    @NotNull
    public String getProjectName() { return this.project_name; }
    public void setProjectName(String project_name) { this.project_name = project_name; }

    @NotNull
    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    @NotNull
    @Min(MINIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT)
    @Max(MAXIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT)
    public int getMinNumberOfStudents() { return this.min_number_of_students; }
    public void setMinNumberOfStudents(int min_number_of_students) { this.min_number_of_students = min_number_of_students; }

    @NotNull
    @Min(MINIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT)
    @Max(MAXIMUM_NUMBER_OF_STUDENTS_FOR_ANY_PROJECT)
    public int getMaxNumberOfStudents() { return this.max_number_of_students; }
    public void setMaxNumberOfStudents(int max_number_of_students) { this.max_number_of_students = max_number_of_students; }

    @NotNull
    @OneToMany(mappedBy="project", cascade = CascadeType.ALL)
    public List<User> getStudents() { return this.students; }
    public void setStudents(List<User> students) { this.students = students; }

    @NotNull
    public List<Program> getProgramRestrictions() { return this.program_restrictions; }
    public void setProgramRestrictions(List<Program> program_restrictions) { this.program_restrictions = program_restrictions; }

    @Override
    public String toString() {
        return this.description;
    }
}
