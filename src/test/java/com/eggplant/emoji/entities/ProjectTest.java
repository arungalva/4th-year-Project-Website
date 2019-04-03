package com.eggplant.emoji.entities;

import com.eggplant.emoji.repository.ProjectRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectTest {

    @Autowired
    ProjectRepository repo;

    private String projectName = "project test dummy project";

    private Project p;

    @Test
    public void persistProgramRestrictions() {
        this.p = new Project();
        this.p.setProjectName(this.projectName);
        Set<Program> programRestrictions = EnumSet.of(Program.SOFTWARE_ENGINEERING, Program.BIOMEDICAL_ELECTRICAL);
        this.p.setProgramRestrictions(programRestrictions);        
        repo.save(this.p);

        Project p2 = repo.findByProjectName(this.projectName);
        assertTrue(p2.getProgramRestrictions().size() == 2);
        assertTrue(p2.getProgramRestrictions().contains(Program.SOFTWARE_ENGINEERING));
        assertTrue(p2.getProgramRestrictions().contains(Program.BIOMEDICAL_ELECTRICAL));
        repo.delete(this.p);
    }

    @Test
    public void testGetAndSetID() {
        Long id = 12345L;

        this.p = new Project();
        this.p.setId(id);

        assertEquals(id, p.getId());
    }

    @Test
    public void getAndSetDescription() {
        String testDescription = "This is a test description";
        this.p = new Project();
        p.setDescription(testDescription);

        assertEquals(testDescription, p.getDescription());
    }

    @Test
    public void getAndSetProjectName() {
        this.p = new Project();
        p.setProjectName(projectName);

        assertEquals(projectName, p.getProjectName());
    }

    @Test
    public void getAndSetMinNumberOfStudents() {
        int testMinNumberOfStudents = 2;
        this.p = new Project();
        p.setMinNumberOfStudents(testMinNumberOfStudents);

        assertEquals(testMinNumberOfStudents, p.getMinNumberOfStudents());
    }

    @Test
    public void getAndSetMaxNumberOfStudents() {
        int testMaxNumberOfStudents = 5;
        this.p = new Project();
        p.setMaxNumberOfStudents(testMaxNumberOfStudents);

        assertEquals(testMaxNumberOfStudents, p.getMaxNumberOfStudents());
    }

    @Test
    public void getAndSetArchiveDate() {
        Date date = new Date();
        this.p = new Project();
        p.setArchivedDate(date);

        assertEquals(date, p.getArchivedDate());
    }

    @Test
    public void addAndRemoveStudents() {
        User student = new User();
        User student2 = new User();

        this.p = new Project();
        p.addStudent(student);
        p.addStudent(student2);

        assertEquals(true, p.removeStudent(student));
    }
}
