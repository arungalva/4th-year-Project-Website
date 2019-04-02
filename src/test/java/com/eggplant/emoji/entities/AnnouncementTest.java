package com.eggplant.emoji.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class AnnouncementTest {

    @Test
    public void getAndSetId() {
        Long id = 12345L;
        Announcement notice = new Announcement();
        notice.setId(id);

        assertEquals(id, notice.getId());
    }

    @Test
    public void getAndSetDescription() {
        String testDescription = "This is a test description";
        Announcement notice = new Announcement();
        notice.setDescription(testDescription);

        assertEquals(testDescription, notice.getDescription());
    }
}