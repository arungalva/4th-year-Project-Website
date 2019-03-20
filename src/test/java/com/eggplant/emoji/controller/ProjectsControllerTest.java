package com.eggplant.emoji.controller;

import com.eggplant.emoji.entities.Program;
import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.service.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectService projectService;

    /**
     * Tests if the /project/add page loads correctly
     * @throws Exception
     */
    @Test
    public void addProjectLoads() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/project/add"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Add New Project")))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("addproject", modelAndView.getViewName());

    }

    /**
     * Sends a POST request with a new project and tests if that project was added to the database
     * @throws Exception
     */
    @Test
    public void addProject() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/project/add")
                .param("projectName","Test Project for add project")
                .param("description","Test Project Description")
                .param("minNumberOfStudents","2")
                .param("maxNumberOfStudents","5")
                .param("programRestrictions", "BIOMEDICAL_ELECTRICAL",
                                                            "ELECTRICAL_ENGINEERING",
                                                            "COMPUTER_SYSTEMS_ENGINEERING"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Project for add project")))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("professor", modelAndView.getViewName());

        Project addedProject = this.projectService.getProjectByName("Test Project for add project");
        assertNotNull(addedProject);
        assertEquals("Test Project for add project", addedProject.getProjectName());
        assertEquals("Test Project Description", addedProject.getDescription());
        assertEquals(2, addedProject.getMinNumberOfStudents());
        assertEquals(5, addedProject.getMaxNumberOfStudents());
        //remove the project that we tested
        this.projectService.removeProjectByName("Test Project for add project");

    }

    /**
     * Sends a POST request with a new project and tests if that project was added to the database
     * @throws Exception
     */
    @Test
    public void getProject() throws Exception {
        // add a test project through out the add projects page
        this.mockMvc.perform(post("/project/add")
                .param("projectName","Test Project for get projects test")
                .param("description","Test Project Description")
                .param("minNumberOfStudents","2")
                .param("maxNumberOfStudents","5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Project")))
                .andReturn();

        MvcResult result = this.mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("4th-Year-Project-projects")))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());

        Project projectExp = this.projectService.getProjectByName("Test Project for get projects test");

        assertNotNull(projectExp);
        assertEquals("Test Project for get projects test", projectExp.getProjectName());
        assertEquals("Test Project Description", projectExp.getDescription());
        assertEquals(2, projectExp.getMinNumberOfStudents());
        assertEquals(5, projectExp.getMaxNumberOfStudents());

        this.projectService.removeProjectByName("Test Project for get projects test");
    }

    /**
     * Tests if the archivedProjects page loads correctly
     * @throws Exception
     */
    @Test
    public void archivedProjectsPage() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/archivedProjects"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("All Archived projects")))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("archivedProjects", modelAndView.getViewName());

    }

    /**
     * Sends a POST request to add a project to the DB and test if that project was removed to the database
     * @throws Exception
     */
    @Test
    public void archiveProject() throws Exception {
        this.projectService.removeProjectByName("Test Project for archive project");

        MvcResult result = this.mockMvc.perform(post("/project/add")
                .param("projectName","Test Project for archive project")
                .param("description","Test Project Description")
                .param("minNumberOfStudents","2")
                .param("maxNumberOfStudents","5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Project for archive project")))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("professor", modelAndView.getViewName());

        Project addedProject = this.projectService.getProjectByName("Test Project for archive project");

        assertNotNull(addedProject);
        assertNull(addedProject.getArchivedDate());

        addedProject.archiveProject();
        projectService.updateProject(addedProject);

        assertNotNull(projectService.getProjectByName("Test Project for archive project").getArchivedDate());

        this.projectService.removeProjectByName("Test Project for archive project");

    }
}
