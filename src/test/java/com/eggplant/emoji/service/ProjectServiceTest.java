package com.eggplant.emoji.service;

import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.repository.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

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

    @Test
    public void test_archive_project_order_by_asc() {
        Project dummyProject1 = new Project();
        Project dummyProject2 = new Project();
        Project dummyProject3 = new Project();

        dummyProject1.setProjectName("Test archive1");
        dummyProject2.setProjectName("Test archive2");
        dummyProject3.setProjectName("Test archive3");

        service.addProject(dummyProject1);
        service.addProject(dummyProject2);
        service.addProject(dummyProject3);

        assertNotNull(service.findById(dummyProject1.getId()));
        assertNotNull(service.findById(dummyProject2.getId()));
        assertNotNull(service.findById(dummyProject3.getId()));

        assertNull(service.findById(dummyProject1.getId()).getArchivedDate());
        assertNull(service.findById(dummyProject2.getId()).getArchivedDate());
        assertNull(service.findById(dummyProject3.getId()).getArchivedDate());

        Project three = service.findById(dummyProject3.getId());
        three.archiveProject();
        service.updateProject(three);
        assertNotNull(three.getArchivedDate());

        Project one = service.findById(dummyProject1.getId());
        one.archiveProject();
        service.updateProject(one);
        assertNotNull(one.getArchivedDate());

        List<Project> allarchivedprojects = service.getAllArchivedProjects();
        assertNotEquals(allarchivedprojects.size(), 0);
        assertEquals(dummyProject1.getId(), allarchivedprojects.get(0).getId());
        assertEquals(dummyProject3.getId(), allarchivedprojects.get(1).getId());
    }
}
