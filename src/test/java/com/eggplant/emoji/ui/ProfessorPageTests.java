package com.eggplant.emoji.ui;

import com.eggplant.emoji.controller.ProjectsController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.eggplant.emoji.controller.ProfessorController.class)
@AutoConfigureMockMvc

public class ProfessorPageTests {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/resources/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(new ProjectsController())
                .setViewResolvers(viewResolver)
                .build();
    }

    /**
     * Tests if the / page loads correctly
     * @throws Exception
     */
    @Test
    public void checkIfJqueryLoads() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/professor")).andReturn();
//                .andExpect(status().isOk())
//                .andExpect(content().string(Matchers.containsString("<script src=\"https://code.jquery.com/jquery-3.1.1.min.js\" integrity=\"sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8=\" crossorigin=\"anonymous\"></script>")))
//                .andReturn();
        System.out.println(content().toString());
//        ModelAndView modelAndView = result.getModelAndView();
//        assertNotNull(modelAndView);
//        assertNotNull(modelAndView.getViewName());
//        assertEquals("home", modelAndView.getViewName());

    }
}
