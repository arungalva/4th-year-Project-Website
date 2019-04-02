package com.eggplant.emoji.controller;

import com.eggplant.emoji.entities.Role;
import com.eggplant.emoji.entities.User;
import com.eggplant.emoji.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithAnonymousUser
    public void getLoginTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/login")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Login to your account")))
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("login", modelAndView.getViewName());

        String contents = result.getResponse().getContentAsString();
        assert(contents.contains("Email"));
        assert(contents.contains("password"));
        assert(contents.contains("Login"));
    }

    @Test
    public void testPostLogin() throws Exception {

        String firstName = "John";
        String lastName = "Doe";
        String email = "johndoe@cmail.carleton.ca";
        int memberId = 100900000;
        Role role = Role.STUDENT;
        String password = "TestingPassword";

        MvcResult result = this.mockMvc.perform(post("/signup")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("email", email)
                .param("memberId", String.valueOf(memberId))
                .param("role", role.toString())
                .param("password", password))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/projects"))
                .andReturn();

        User signedUpUser = this.userService.getUserByEmail(email);
        assertNotNull(signedUpUser);
        assertEquals(email, signedUpUser.getEmail());
        assertTrue(passwordEncoder.matches(password, signedUpUser.getPassword()));


        RequestBuilder requestBuilder = formLogin().user(email).password(password);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername(email))
                .andReturn();

        mockMvc
                .perform(logout())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/login?logout"));

    }

    /**
     * Test that authenticated users cannot access the login page
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "John", authorities = {"STUDENT"})
    public void getStudentLoads() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/login"))
                .andExpect(status().is4xxClientError())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getForwardedUrl());
        assertEquals("/accessdenied", mockHttpServletResponse.getForwardedUrl());
    }

    @Test
    public void invalidLoginDenied() throws Exception {
        String loginErrorUrl = "/login?error";
        mockMvc
                .perform(formLogin().password("invalid"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(loginErrorUrl))
                .andExpect(unauthenticated());
    }
}
