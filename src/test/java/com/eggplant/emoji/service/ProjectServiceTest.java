package com.eggplant.emoji.service;

import com.eggplant.emoji.entities.Program;
import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.repository.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

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

        Project p1 = service.addProject(dummyProject1);
        Project p2 = service.addProject(dummyProject2);
        Project p3 = service.addProject(dummyProject3);

        assertNotNull(service.findById(p1.getId()));
        assertNotNull(service.findById(p2.getId()));
        assertNotNull(service.findById(p3.getId()));

        assertNull(p1.getArchivedDate());
        assertNull(p2.getArchivedDate());
        assertNull(p3.getArchivedDate());

        p3.archiveProject();
        service.updateProject(p3);
        assertNotNull(p3.getArchivedDate());

        p1.archiveProject();
        service.updateProject(p1);
        assertNotNull(p1.getArchivedDate());

        List<Project> allarchivedprojects = service.getAllArchivedProjects();
        assertNotEquals(allarchivedprojects.size(), 0);
        assertEquals(p1.getId(), allarchivedprojects.get(0).getId());
        assertEquals(p3.getId(), allarchivedprojects.get(1).getId());

        service.removeProjectByName(p1.getProjectName());
        service.removeProjectByName(p2.getProjectName());
        service.removeProjectByName(p3.getProjectName());

    }

    @Test
    public void testSaveAndFlush(){
        Project dummyProject = new Project();
        dummyProject.setProjectName("Test Project for testing individual pages");
        dummyProject.setDescription("This is a random project description");
        dummyProject.setMinNumberOfStudents(2);
        dummyProject.setMaxNumberOfStudents(5);
        Set<Program> ProgramRestrictions = EnumSet.allOf(Program.class);
        dummyProject.setProgramRestrictions(ProgramRestrictions);

        Project p = service.addProject(dummyProject);

        assertEquals(dummyProject, p);

        service.removeProjectByName(p.getProjectName());

    }
}
