package com.eggplant.emoji.entities;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.eggplant.emoji.entities.User.Role;
import com.eggplant.emoji.entities.User.Program;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    List<Project> findById(int id);
    List<Project> findByCreatedBy(User createdBy);
    List<Project> findByProjectName(String project_name);
    List<Project> findByStudentsContains(User student);
    List<Project> findByProgramRestrictionsContains(Program program);

    @Modifying
    @Transactional
    void deleteById(int id);
}