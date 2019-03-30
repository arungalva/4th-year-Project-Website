package com.eggplant.emoji.repository;

import com.eggplant.emoji.entities.Announcement;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
    Announcement findByDescription(String description);
    List<Announcement> findAllByOrderByIdAsc();

    @Modifying
    @Transactional
    void deleteAnnouncementById(Long id);
}
