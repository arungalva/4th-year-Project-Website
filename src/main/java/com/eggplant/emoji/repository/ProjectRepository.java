package com.eggplant.emoji.repository;

import com.eggplant.emoji.entities.Project;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByProjectName(String projectName);
    List<Project> findAllByOrderByIdAsc();

    @Modifying
    @Transactional
    void deleteProjectByProjectName(String projectName);

    List<Project> findAllByArchivedDateNull();

    List<Project> findAllByArchivedDateNotNullOrderByIdAsc();
}