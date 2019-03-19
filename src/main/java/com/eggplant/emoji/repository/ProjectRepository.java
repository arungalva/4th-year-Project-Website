package com.eggplant.emoji.repository;

import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByProjectName(String projectName);
    void deleteProjectByProjectName(String projectName);


}