package com.eggplant.emoji.app;

import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.entities.User;
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
        @SuppressWarnings("unchecked")
        List<Project> projects = (List<Project>) modelAndView.getModel().get("projects");
        Project projectExp = null;
        for(Project project : projects){
            if(project.getProjectName().equals("Test Project")){
                projectExp = project;
            }
        }
        assertNotNull(projectExp);
        assertEquals("Test Project", projectExp.getProjectName());
        assertEquals("Test Project Description", projectExp.getDescription());
        assertEquals(2, projectExp.getMinNumberOfStudents());
        assertEquals(5, projectExp.getMaxNumberOfStudents());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-test");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Query q = em.createQuery("SELECT p FROM Project p WHERE p.projectName = 'Test Project'");
        @SuppressWarnings("unchecked")
        List<Project> results = q.getResultList();
        assertEquals(1, results.size());
        tx.begin();
        em.remove(results.get(0));
        tx.commit();

        em.close();
        emf.close();
    }
}
