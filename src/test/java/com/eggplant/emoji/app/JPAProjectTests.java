package com.eggplant.emoji.app;

import com.eggplant.emoji.entities.Project;
import org.junit.After;
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
public class JPAProjectTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Used to clear all the project entities added in the test cases
     * This is done so that later tests can be performed with an empty project table
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-test");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Query q = em.createQuery("SELECT p FROM Project p");
        @SuppressWarnings("unchecked")
        List<Project> results = q.getResultList();
        tx.begin();
        for(Project project : results){
            em.remove(project);
        }
        tx.commit();
        tx.begin();
        em.clear();
        tx.commit();
    }

    /**
     * Tests if the appproject page loads correctly
     * @throws Exception
     */
    @Test
    public void addProjectLoads() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/addproject"))
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
        MvcResult result = this.mockMvc.perform(post("/addproject")
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
        @SuppressWarnings("unchecked")
        List<Project> projects = (List<Project>) modelAndView.getModel().get("projects");
        assertEquals(1, projects.size());
        assertEquals("Test Project", projects.get(0).getProjectName());
        assertEquals("Test Project Description", projects.get(0).getDescription());
        assertEquals(2, projects.get(0).getMinNumberOfStudents());
        assertEquals(5, projects.get(0).getMaxNumberOfStudents());
    }
}
