package com.eggplant.emoji.controller;

import com.eggplant.emoji.entities.Role;
import com.eggplant.emoji.entities.User;
import com.eggplant.emoji.service.UserService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    /**
     * Sends a POST request with a new project and tests if that project was added to the database
     * @throws Exception
     */
    @Test
    @WithAnonymousUser
    public void signup() throws Exception {
        String firstName = "Arun";
        String lastName = "Galva";
        String email = "arungalva@cmail.carleton.ca";
        int memberId = 100976147;
        Role role = Role.STUDENT;
        String password = "TestPassword";

        try {
            MvcResult result = this.mockMvc.perform(post("/signup")
                    .param("firstName", firstName)
                    .param("lastName", lastName)
                    .param("email", email)
                    .param("memberId", String.valueOf(memberId))
                    .param("role", role.toString())
                    .param("password", password))
                    .andExpect(status().isFound())
                    .andReturn();
            ModelAndView modelAndView = result.getModelAndView();
            assertNotNull(modelAndView);
            assertNotNull(modelAndView.getViewName());
            assertEquals("redirect:/", modelAndView.getViewName());

            User signedUpUser = this.userService.getUserByEmail(email);
            assertNotNull(signedUpUser);
            assertEquals(firstName, signedUpUser.getFirstName());
            assertEquals(lastName, signedUpUser.getLastName());
            assertEquals(email, signedUpUser.getEmail());
            assertEquals(memberId, signedUpUser.getMemberId());
            assertEquals(role, signedUpUser.getRole());
            this.userService.deleteByEmail(email);
        } catch (Exception e) {
            //remove the project that we tested
            this.userService.deleteByEmail(email);
            throw e;
        }
    }

    /**
     * Test that authenticated users cannot access the login page
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "John", authorities = {"STUDENT"})
    public void getStudentLoads() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/signup"))
                .andExpect(status().is4xxClientError())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getForwardedUrl());
        assertEquals("/accessdenied", mockHttpServletResponse.getForwardedUrl());
    }
}
