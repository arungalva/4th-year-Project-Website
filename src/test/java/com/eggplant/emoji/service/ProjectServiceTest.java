package com.eggplant.emoji.service;

import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.repository.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    ProjectService service;

    @Autowired
    ProjectRepository repo;

    @Test
    public void test_add_new_project(){
        Project dummyProject = new Project();
        dummyProject.setProjectName("Test add new project");

        service.addProject(dummyProject);

        // verify it got saved
        Project p = repo.findByProjectName("Test add new project");
        assertTrue(p.getProjectName().equals(dummyProject.getProjectName()));
        assertTrue(p.getDescription().equals(dummyProject.getDescription()));
        assertTrue(p.getMaxNumberOfStudents() == dummyProject.getMaxNumberOfStudents());
        assertTrue(p.getMinNumberOfStudents() == dummyProject.getMinNumberOfStudents());

        //remove the project
        repo.deleteProjectByProjectName(dummyProject.getProjectName());
    }

    @Test
    public void test_delete_project() {
        Project dummyProject = new Project();
        dummyProject.setProjectName("Test delete new project");

        service.addProject(dummyProject);
        service.removeProjectByName(dummyProject.getProjectName());

        assertTrue(repo.findByProjectName(dummyProject.getProjectName()) == null);

    }
}
