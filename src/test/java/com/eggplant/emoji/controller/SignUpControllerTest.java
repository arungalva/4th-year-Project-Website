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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    @After
    public void tearDown() {
        userService.removeAllUsers();
    }

    /**
     * Sends a POST request with a new project and tests if that project was added to the database
     * @throws Exception
     */
    @Test
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
            assertEquals("redirect:/projects", modelAndView.getViewName());

            User signedUpUser = this.userService.getUserByEmail(email);
            System.out.println("signedUpUser is:" + signedUpUser);
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
}
