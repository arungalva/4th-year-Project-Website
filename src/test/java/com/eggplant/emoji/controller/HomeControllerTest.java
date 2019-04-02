package com.eggplant.emoji.controller;


import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Tests if the / page loads correctly
	 * @throws Exception
	 */
	@Test
	public void homePage() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string(Matchers.containsString("Welcome to SYSC 4907 - Engineering Project")))
				.andReturn();
		ModelAndView modelAndView = result.getModelAndView();
		assertNotNull(modelAndView);
		assertNotNull(modelAndView.getViewName());
		assertEquals("home", modelAndView.getViewName());

	}

	/**
	 * Tests if the /deadlines page loads correctly
	 * @throws Exception
	 */
	@Test
	public void deadlinesPage() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/deadlines"))
				.andExpect(status().isOk())
				.andExpect(content().string(Matchers.containsString("2018/2019 Project Deadlines")))
				.andReturn();
		ModelAndView modelAndView = result.getModelAndView();
		assertNotNull(modelAndView);
		assertNotNull(modelAndView.getViewName());
		assertEquals("deadlines", modelAndView.getViewName());

	}

	/**
	 * Tests if the /projects page loads correctly
	 * @throws Exception
	 */
	@Test
	public void projectsPage() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/projects"))
				.andExpect(status().isOk())
				.andExpect(content().string(Matchers.containsString("All projects")))
				.andReturn();
		ModelAndView modelAndView = result.getModelAndView();
		assertNotNull(modelAndView);
		assertNotNull(modelAndView.getViewName());
		assertEquals("projects", modelAndView.getViewName());

	}

	/**
	 * Tests that students can see the student view button but not the professor view button
	 * @throws Exception
	 */
	@Test
	@WithMockUser(username = "John", authorities = {"STUDENT"})
	public void testStudentLoggedInView() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string(Matchers.containsString("Student View")))
				.andExpect(content().string(Matchers.not(Matchers.containsString("Professor View"))))
				.andReturn();
		ModelAndView modelAndView = result.getModelAndView();
		assertNotNull(modelAndView);
		assertNotNull(modelAndView.getViewName());
		assertEquals("home", modelAndView.getViewName());
	}

	/**
	 * Tests that professors can see the professor view button but not the student view button
	 * @throws Exception
	 */
	@Test
	@WithMockUser(username = "John", authorities = {"PROFESSOR"})
	public void testProfessorLoggedInView() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string(Matchers.containsString("Professor View")))
				.andExpect(content().string(Matchers.not(Matchers.containsString("Student View"))))
				.andReturn();
		ModelAndView modelAndView = result.getModelAndView();
		assertNotNull(modelAndView);
		assertNotNull(modelAndView.getViewName());
		assertEquals("home", modelAndView.getViewName());
	}

	/**
	 * Tests that annonymous users cannot see the student view and professor view buttons
	 * @throws Exception
	 */
	@Test
	@WithAnonymousUser
	public void testAnonymousView() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string(Matchers.not(Matchers.containsString("Professor View"))))
				.andExpect(content().string(Matchers.not(Matchers.containsString("Student View"))))
				.andReturn();
		ModelAndView modelAndView = result.getModelAndView();
		assertNotNull(modelAndView);
		assertNotNull(modelAndView.getViewName());
		assertEquals("home", modelAndView.getViewName());
	}
}
