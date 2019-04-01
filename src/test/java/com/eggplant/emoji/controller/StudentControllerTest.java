package com.eggplant.emoji.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.entities.Role;
import com.eggplant.emoji.entities.User;
import com.eggplant.emoji.repository.UserRepository;
import com.eggplant.emoji.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserService service;
    
    @Autowired
    UserRepository repo;

    private Project p;
    private User u;

    private String dummyName = "Dummy";
    private String dummyEmail = "dum@carleton.ca";
    private int dummyMemberID = 100123122;
    private String dummyPassword = "password";

    /**
     * Tests if the /student page loads correctly
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "John", authorities = {"STUDENT"})
    public void getStudentLoads() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Student Overview")))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("student", modelAndView.getViewName());
    }

    /**
     * Tests that a professor cannot access /student page
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "John", authorities = {"PROFESSOR"})
    public void testProfessorAccess() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/student"))
                .andExpect(status().is4xxClientError())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getForwardedUrl());
        assertEquals("/accessdenied", mockHttpServletResponse.getForwardedUrl());
    }

    /**
     * Tests that annonymous users cannot access /student page
     * @throws Exception
     */
    @Test
    @WithAnonymousUser
    public void testAnonymousAccess() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/student"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getRedirectedUrl());
        assertTrue( mockHttpServletResponse.getRedirectedUrl().endsWith("/login"));
    }

    /**
     * Tests if the a student can join a project and have that persisted
     * @throws Exception
     */
    @Test
    public void joinProject() throws Exception {
        p = new Project();
        p.setId((long) 3);
        this.mockMvc.perform(post("/signup")
        .param("firstName", this.dummyName)
        .param("lastName", this.dummyName)
        .param("email", this.dummyEmail)
        .param("memberId", Integer.toString(this.dummyMemberID))
        .param("role", Role.STUDENT.toString())
        .param("password", this.dummyPassword))
        .andExpect(status().isFound())
        .andReturn();

        u = repo.findByEmail(this.dummyEmail);

        RequestBuilder requestBuilder = formLogin().user(u.getEmail()).password(u.getPassword());
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isFound());

        this.mockMvc.perform(get("/joinproject")
            .param("id", p.getId().toString()))
            .andReturn();
    }
}
