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
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

}
