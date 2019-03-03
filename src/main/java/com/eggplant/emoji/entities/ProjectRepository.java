package com.eggplant.emoji.entities;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;

import com.eggplant.emoji.entities.User.Program;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    List<Project> findById(int id);
    List<Project> findByCreatedBy(User createdBy);
    List<Project> findByProjectName(String projectName);
    List<Project> findByStudentsContains(User student);
    List<Project> findByCreatedDateAfter(Date date);
    List<Project> findByCreatedDateBefore(Date date);
    List<Project> findByModifiedDateAfter(Date date);
    List<Project> findByModifiedDateBefore(Date date);
    List<Project> findByProgramRestrictionsContains(Program program);

    @Modifying
    @Transactional
    void deleteById(int id);
}