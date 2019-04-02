package com.eggplant.emoji.entities;

import com.eggplant.emoji.repository.ProjectRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.EnumSet;
import java.util.Set;

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
}
