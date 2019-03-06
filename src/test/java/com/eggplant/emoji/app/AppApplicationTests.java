package com.eggplant.emoji.app;


import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppApplicationTests {

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
	 * Tests if the /notices page loads correctly
	 * @throws Exception
	 */
	@Test
	public void noticesPage() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/notices"))
				.andExpect(status().isOk())
				.andExpect(content().string(Matchers.containsString("2018/2019 Project Notices")))
				.andReturn();
		ModelAndView modelAndView = result.getModelAndView();
		assertNotNull(modelAndView);
		assertNotNull(modelAndView.getViewName());
		assertEquals("notices", modelAndView.getViewName());

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
}
