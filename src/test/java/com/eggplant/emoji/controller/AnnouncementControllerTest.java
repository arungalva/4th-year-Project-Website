package com.eggplant.emoji.controller;

import com.eggplant.emoji.entities.Announcement;
import com.eggplant.emoji.service.AnnouncementService;
import com.eggplant.emoji.service.ProjectService;
import org.hamcrest.Matchers;
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

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnnouncementService announcementService;

    @Test
    @WithMockUser(username = "John", authorities = {"COORDINATOR"})
    public void index() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/announcements"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Add Announcement")))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("announcements", modelAndView.getViewName());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"STUDENT"})
    public void indexStudent() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/announcements"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.not(Matchers.containsString("Add Announcement"))))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("announcements", modelAndView.getViewName());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"PROFESSOR"})
    public void indexProfessor() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/announcements"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.not(Matchers.containsString("Add Announcement"))))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("announcements", modelAndView.getViewName());
    }

    @Test
    @WithAnonymousUser
    public void indexAnonymous() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/announcements"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.not(Matchers.containsString("Add Announcement"))))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("announcements", modelAndView.getViewName());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"COORDINATOR"})
    public void getAddAnnouncement() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/announcement/add"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Add New Announcement")))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("addannouncement", modelAndView.getViewName());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"STUDENT"})
    public void getAddAnnouncementStudent() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/announcement/add"))
                .andExpect(status().is4xxClientError())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getForwardedUrl());
        assertEquals("/accessdenied", mockHttpServletResponse.getForwardedUrl());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"PROFESSOR"})
    public void getAddAnnouncementProfessor() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/announcement/add"))
                .andExpect(status().is4xxClientError())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getForwardedUrl());
        assertEquals("/accessdenied", mockHttpServletResponse.getForwardedUrl());
    }

    @Test
    @WithAnonymousUser
    public void getAddAnnouncementAnonymous() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/announcement/add"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getRedirectedUrl());
        assertTrue( mockHttpServletResponse.getRedirectedUrl().endsWith("/login"));
    }

    @Test
    @WithMockUser(username = "John", authorities = {"COORDINATOR"})
    public void getEditAnnouncement() throws Exception {
        Announcement testAnnouncement = new Announcement("getEditAnnouncement Description");
        announcementService.addAnnouncement(testAnnouncement);

        MvcResult result = this.mockMvc.perform(get("/announcement/edit?id=" + testAnnouncement.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Edit Announcement #" + testAnnouncement.getId())))
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("editannouncement", modelAndView.getViewName());

        announcementService.removeAnnouncementById(testAnnouncement.getId());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"STUDENT"})
    public void getEditAnnouncementStudent() throws Exception {
        Announcement testAnnouncement = new Announcement("getEditAnnouncement Description");
        announcementService.addAnnouncement(testAnnouncement);

        MvcResult result = this.mockMvc.perform(get("/announcement/edit?id=" + testAnnouncement.getId()))
                .andExpect(status().is4xxClientError())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getForwardedUrl());
        assertEquals("/accessdenied", mockHttpServletResponse.getForwardedUrl());

        announcementService.removeAnnouncementById(testAnnouncement.getId());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"PROFESSOR"})
    public void getEditAnnouncementProfessor() throws Exception {
        Announcement testAnnouncement = new Announcement("getEditAnnouncement Description");
        announcementService.addAnnouncement(testAnnouncement);

        MvcResult result = this.mockMvc.perform(get("/announcement/edit?id=" + testAnnouncement.getId()))
                .andExpect(status().is4xxClientError())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getForwardedUrl());
        assertEquals("/accessdenied", mockHttpServletResponse.getForwardedUrl());

        announcementService.removeAnnouncementById(testAnnouncement.getId());
    }

    @Test
    @WithAnonymousUser
    public void getEditAnnouncementAnonymous() throws Exception {
        Announcement testAnnouncement = new Announcement("getEditAnnouncement Description");
        announcementService.addAnnouncement(testAnnouncement);

        MvcResult result = this.mockMvc.perform(get("/announcement/edit?id=" + testAnnouncement.getId()))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getRedirectedUrl());
        assertTrue( mockHttpServletResponse.getRedirectedUrl().endsWith("/login"));

        announcementService.removeAnnouncementById(testAnnouncement.getId());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"COORDINATOR"})
    public void postAddAnnouncement() throws Exception {
        String announcementDescription = "postAddAnnouncement Description";
        MvcResult result = this.mockMvc.perform(post("/announcement/add")
                .param("description",announcementDescription))
                .andExpect(status().isFound())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("redirect:/announcements", modelAndView.getViewName());

        Announcement addedAnnouncement = announcementService.findByDescription(announcementDescription);
        assertNotNull(addedAnnouncement);

        announcementService.removeAnnouncementById(addedAnnouncement.getId());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"STUDENT"})
    public void postAddAnnouncementStudent() throws Exception {
        String announcementDescription = "postAddAnnouncement Description";
        MvcResult result = this.mockMvc.perform(post("/announcement/add")
                .param("description",announcementDescription))
                .andExpect(status().is4xxClientError())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getForwardedUrl());
        assertEquals("/accessdenied", mockHttpServletResponse.getForwardedUrl());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"PROFESSOR"})
    public void postAddAnnouncementProfessor() throws Exception {
        String announcementDescription = "postAddAnnouncement Description";
        MvcResult result = this.mockMvc.perform(post("/announcement/add")
                .param("description",announcementDescription))
                .andExpect(status().is4xxClientError())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getForwardedUrl());
        assertEquals("/accessdenied", mockHttpServletResponse.getForwardedUrl());
    }

    @Test
    @WithAnonymousUser
    public void postAddAnnouncementAnonymous() throws Exception {
        String announcementDescription = "postAddAnnouncement Description";
        MvcResult result = this.mockMvc.perform(post("/announcement/add")
                .param("description",announcementDescription))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getRedirectedUrl());
        assertTrue( mockHttpServletResponse.getRedirectedUrl().endsWith("/login"));
    }

    @Test
    @WithMockUser(username = "John", authorities = {"COORDINATOR"})
    public void postEditAnnouncement() throws Exception {
        String announcementDescription = "postEditAnnouncement Description";
        String editedAnnouncementDescription = "(edited) postEditAnnouncement Description";
        Announcement editAnnouncement = new Announcement(announcementDescription);
        announcementService.addAnnouncement(editAnnouncement);

        MvcResult result = this.mockMvc.perform(post("/announcement/edit?id=" + editAnnouncement.getId())
                .param("announcement-id",editAnnouncement.getId().toString())
                .param("description",editedAnnouncementDescription))
                .andExpect(status().isFound())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("redirect:/announcements", modelAndView.getViewName());

        Announcement editedAnnouncement = announcementService.findById(editAnnouncement.getId());
        assertNotNull(editedAnnouncement);
        assertEquals(editedAnnouncementDescription, editedAnnouncement.getDescription());

        announcementService.removeAnnouncementById(editedAnnouncement.getId());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"STUDENT"})
    public void postEditAnnouncementStudent() throws Exception {
        String announcementDescription = "postEditAnnouncement Description";
        String editedAnnouncementDescription = "(edited) postEditAnnouncement Description";
        Announcement editAnnouncement = new Announcement(announcementDescription);
        announcementService.addAnnouncement(editAnnouncement);

        MvcResult result = this.mockMvc.perform(post("/announcement/edit?id=" + editAnnouncement.getId())
                .param("announcement-id",editAnnouncement.getId().toString())
                .param("description",editedAnnouncementDescription))
                .andExpect(status().is4xxClientError())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getForwardedUrl());
        assertEquals("/accessdenied", mockHttpServletResponse.getForwardedUrl());

        Announcement editedAnnouncement = announcementService.findById(editAnnouncement.getId());
        announcementService.removeAnnouncementById(editedAnnouncement.getId());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"PROFESSOR"})
    public void postEditAnnouncementProfessor() throws Exception {
        String announcementDescription = "postEditAnnouncement Description";
        String editedAnnouncementDescription = "(edited) postEditAnnouncement Description";
        Announcement editAnnouncement = new Announcement(announcementDescription);
        announcementService.addAnnouncement(editAnnouncement);

        MvcResult result = this.mockMvc.perform(post("/announcement/edit?id=" + editAnnouncement.getId())
                .param("announcement-id",editAnnouncement.getId().toString())
                .param("description",editedAnnouncementDescription))
                .andExpect(status().is4xxClientError())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getForwardedUrl());
        assertEquals("/accessdenied", mockHttpServletResponse.getForwardedUrl());

        Announcement editedAnnouncement = announcementService.findById(editAnnouncement.getId());
        announcementService.removeAnnouncementById(editedAnnouncement.getId());
    }

    @Test
    @WithAnonymousUser
    public void postEditAnnouncementAnonymous() throws Exception {
        String announcementDescription = "postEditAnnouncement Description";
        String editedAnnouncementDescription = "(edited) postEditAnnouncement Description";
        Announcement editAnnouncement = new Announcement(announcementDescription);
        announcementService.addAnnouncement(editAnnouncement);

        MvcResult result = this.mockMvc.perform(post("/announcement/edit?id=" + editAnnouncement.getId())
                .param("announcement-id",editAnnouncement.getId().toString())
                .param("description",editedAnnouncementDescription))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getRedirectedUrl());
        assertTrue( mockHttpServletResponse.getRedirectedUrl().endsWith("/login"));

        Announcement editedAnnouncement = announcementService.findById(editAnnouncement.getId());
        announcementService.removeAnnouncementById(editedAnnouncement.getId());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"COORDINATOR"})
    public void removeAnnouncement() throws Exception {
        String announcementDescription = "removeAnnouncement Description";
        Announcement removeAnnouncement = new Announcement(announcementDescription);
        announcementService.addAnnouncement(removeAnnouncement);

        MvcResult result = this.mockMvc.perform(post("/announcement/delete")
                .param("id",removeAnnouncement.getId().toString()))
                .andExpect(status().isFound())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertNotNull(modelAndView.getViewName());
        assertEquals("redirect:/announcements", modelAndView.getViewName());

        Announcement removedAnnouncement = announcementService.findById(removeAnnouncement.getId());
        assertNull(removedAnnouncement);
    }

    @Test
    @WithMockUser(username = "John", authorities = {"STUDENT"})
    public void removeAnnouncementStudent() throws Exception {
        String announcementDescription = "removeAnnouncement Description";
        Announcement removeAnnouncement = new Announcement(announcementDescription);
        announcementService.addAnnouncement(removeAnnouncement);

        MvcResult result = this.mockMvc.perform(post("/announcement/delete")
                .param("id",removeAnnouncement.getId().toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getForwardedUrl());
        assertEquals("/accessdenied", mockHttpServletResponse.getForwardedUrl());

        Announcement removedAnnouncement = announcementService.findById(removeAnnouncement.getId());
        assertNotNull(removedAnnouncement);
        announcementService.removeAnnouncementById(removeAnnouncement.getId());
    }

    @Test
    @WithMockUser(username = "John", authorities = {"PROFESSOR"})
    public void removeAnnouncementProfessor() throws Exception {
        String announcementDescription = "removeAnnouncement Description";
        Announcement removeAnnouncement = new Announcement(announcementDescription);
        announcementService.addAnnouncement(removeAnnouncement);

        MvcResult result = this.mockMvc.perform(post("/announcement/delete")
                .param("id",removeAnnouncement.getId().toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getForwardedUrl());
        assertEquals("/accessdenied", mockHttpServletResponse.getForwardedUrl());

        Announcement removedAnnouncement = announcementService.findById(removeAnnouncement.getId());
        assertNotNull(removedAnnouncement);
        announcementService.removeAnnouncementById(removeAnnouncement.getId());
    }

    @Test
    @WithAnonymousUser
    public void removeAnnouncementAnonymous() throws Exception {
        String announcementDescription = "removeAnnouncement Description";
        Announcement removeAnnouncement = new Announcement(announcementDescription);
        announcementService.addAnnouncement(removeAnnouncement);

        MvcResult result = this.mockMvc.perform(post("/announcement/delete")
                .param("id",removeAnnouncement.getId().toString()))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = result.getResponse();
        assertNotNull(mockHttpServletResponse);
        assertNotNull(mockHttpServletResponse.getRedirectedUrl());
        assertTrue( mockHttpServletResponse.getRedirectedUrl().endsWith("/login"));

        Announcement removedAnnouncement = announcementService.findById(removeAnnouncement.getId());
        assertNotNull(removedAnnouncement);
        announcementService.removeAnnouncementById(removeAnnouncement.getId());
    }
}
