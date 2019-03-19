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

import javax.persistence.*;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
     * Tests if the appproject page loads correctly
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
                .param("projectName","Test Project")
                .param("description","Test Project Description")
                .param("minNumberOfStudents","2")
                .param("maxNumberOfStudents","5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Project")))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("professor", modelAndView.getViewName());

        Project addedProject = this.projectService.getProjectByName("Test Project");
        assertNotNull(addedProject);
        assertEquals("Test Project", addedProject.getProjectName());
        assertEquals("Test Project Description", addedProject.getDescription());
        assertEquals(2, addedProject.getMinNumberOfStudents());
        assertEquals(5, addedProject.getMaxNumberOfStudents());

        //remove the project that we tested
        this.projectService.removeProjectByName("Test Project");

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
}