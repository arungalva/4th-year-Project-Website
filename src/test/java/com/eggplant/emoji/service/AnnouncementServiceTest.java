package com.eggplant.emoji.service;

import com.eggplant.emoji.entities.Announcement;
import com.eggplant.emoji.repository.AnnouncementRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnouncementServiceTest {

    @Autowired
    AnnouncementService announcementService;

    @Test
    public void test_add_new_announcement(){
        String testDescription = "Integration Add Test Announcement";
        Announcement dummyAnnouncement = new Announcement();
        dummyAnnouncement.setDescription(testDescription);

        announcementService.addAnnouncement(dummyAnnouncement);

        // verify it got saved
        Announcement ann = announcementService.findById(dummyAnnouncement.getId());
        assertTrue(ann.getDescription().equals(dummyAnnouncement.getDescription()));

        //remove the announcement
        announcementService.removeAnnouncementById(dummyAnnouncement.getId());
    }

    @Test
    public void test_edit_announcement(){
        String testDescription = "Integration Add Test Announcement";
        String editedTestDescription = "(edited) Integration Add Test Announcement";
        Announcement dummyAnnouncement = new Announcement();
        dummyAnnouncement.setDescription(testDescription);

        announcementService.addAnnouncement(dummyAnnouncement);

        //edit
        dummyAnnouncement.setDescription(editedTestDescription);
        announcementService.updateAnnouncement(dummyAnnouncement);

        // verify it got saved
        Announcement ann = announcementService.findById(dummyAnnouncement.getId());
        assertEquals(editedTestDescription, ann.getDescription());

        //remove the announcement
        announcementService.removeAnnouncementById(dummyAnnouncement.getId());
    }

    @Test
    public void test_delete_announcement() {
        String testDescription = "Integration Delete Test Announcement";
        Announcement dummyAnnouncement = new Announcement();
        dummyAnnouncement.setDescription(testDescription);

        announcementService.addAnnouncement(dummyAnnouncement);
        announcementService.removeAnnouncementById(dummyAnnouncement.getId());

        assertNull(announcementService.findById(dummyAnnouncement.getId()));
    }
}
