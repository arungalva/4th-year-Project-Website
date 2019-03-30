package com.eggplant.emoji.service;

import com.eggplant.emoji.entities.Announcement;
import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public List<Announcement> findAll() { return announcementRepository.findAllByOrderByIdAsc(); }

    public Announcement findById(Long id) {
        if(announcementRepository.findById(id).isPresent()){
            return announcementRepository.findById(id).get();
        }
        return null;
    }

    public void addAnnouncement(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    public boolean updateAnnouncement(Announcement announcement) {
        if(announcementRepository.existsById(announcement.getId())){
            announcementRepository.save(announcement);
            return true;
        }else return false;
    }

    public Announcement findByDescription(String description){ return announcementRepository.findByDescription(description); }

    public void removeAnnouncementById(Long id) { announcementRepository.deleteAnnouncementById(id); }
}
