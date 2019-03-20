package com.eggplant.emoji.controller;

import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.service.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
        String projectName = "addProjectTest name";
        String projectDescription = "addProjectTest description";
        MvcResult result = this.mockMvc.perform(post("/project/add")
                .param("projectName",projectName)
                .param("description",projectDescription)
                .param("minNumberOfStudents","2")
                .param("maxNumberOfStudents","5")
                .param("programRestrictions", "BIOMEDICAL_ELECTRICAL",
                                                            "ELECTRICAL_ENGINEERING",
                                                            "COMPUTER_SYSTEMS_ENGINEERING"))
                .andExpect(status().isFound())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("redirect:/professor", modelAndView.getViewName());

        Project addedProject = this.projectService.getProjectByName(projectName);
        assertNotNull(addedProject);
        assertEquals(projectName, addedProject.getProjectName());
        assertEquals(projectDescription, addedProject.getDescription());
        assertEquals(2, addedProject.getMinNumberOfStudents());
        assertEquals(5, addedProject.getMaxNumberOfStudents());
        //remove the project that we tested
        this.projectService.removeProjectByName(projectName);

    }

    /**
     * Sends a POST request with a new project and tests if that project was added to the database
     * @throws Exception
     */
    @Test
    public void getProject() throws Exception {
        String projectName = "getProjectTest name";
        String projectDescription = "getProjectTest description";
        this.mockMvc.perform(post("/project/add")
                .param("projectName",projectName)
                .param("description",projectDescription)
                .param("minNumberOfStudents","2")
                .param("maxNumberOfStudents","5"))
                .andExpect(status().isFound())
                .andReturn();

        MvcResult result = this.mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("4th-Year-Project-projects")))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());

        Project projectExp = this.projectService.getProjectByName(projectName);

        assertNotNull(projectExp);
        assertEquals(projectName, projectExp.getProjectName());
        assertEquals(projectDescription, projectExp.getDescription());
        assertEquals(2, projectExp.getMinNumberOfStudents());
        assertEquals(5, projectExp.getMaxNumberOfStudents());

        this.projectService.removeProjectByName(projectName);
    }

    /**
     * Sends a POST request with a new project, edits that project,  and tests if that project was edited
     * @throws Exception
     */
    @Test
    public void editProject() throws Exception {
        String projectName = "editProjectTest name";
        String newProjectName = "editProjectTest new name";
        String projectDescription = "editProjectTest description";
        this.mockMvc.perform(post("/project/add")
                .param("projectName",projectName)
                .param("description",projectDescription)
                .param("minNumberOfStudents","2")
                .param("maxNumberOfStudents","5"))
                .andExpect(status().isFound())
                .andReturn();

        Project addedProject = this.projectService.getProjectByName(projectName);
        Long projectId = addedProject.getId();

        this.mockMvc.perform(post("/project/edit")
                .param("id", projectId.toString())
                .param("projectName",newProjectName)
                .param("description",projectDescription)
                .param("minNumberOfStudents","2")
                .param("maxNumberOfStudents","5"))
                .andExpect(status().isFound())
                .andReturn();

        Project editedProject = this.projectService.getProjectByName(newProjectName);
        assertNotNull(editedProject);
        assertEquals(newProjectName, editedProject.getProjectName());
        assertEquals(projectDescription, editedProject.getDescription());
        assertEquals(2, editedProject.getMinNumberOfStudents());
        assertEquals(5, editedProject.getMaxNumberOfStudents());

        //remove the project that we tested
        this.projectService.removeProjectByName(newProjectName);

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
                .andExpect(status().isFound())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("redirect:/professor", modelAndView.getViewName());

        Project addedProject = this.projectService.getProjectByName("Test Project for archive project");

        assertNotNull(addedProject);
        assertNull(addedProject.getArchivedDate());

        addedProject.archiveProject();
        projectService.updateProject(addedProject);

        assertNotNull(projectService.getProjectByName("Test Project for archive project").getArchivedDate());

        this.projectService.removeProjectByName("Test Project for archive project");

    }
}
