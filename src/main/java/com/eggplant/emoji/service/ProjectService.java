package com.eggplant.emoji.service;

import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository repository;

    public List<Project> findAll() {
        List<Project> projects = (List<Project>) repository.findAllByOrderByIdAsc();
        return projects;
    }

    public Project findById(Long id) {
        Project project = repository.findById(id).get();
        return project;
    }

    public void addProject(Project project) {
        repository.save(project);
    }

    public boolean updateProject(Project project) {
        if (repository.findById(project.getId()) == null) {
            return false;
        }
        repository.save(project);
        return true;
    }

    public List<Project> getAllNonArchivedProjects() { return repository.findAllByArchivedDateNull();};
    public List<Project> getAllArchivedProjects() { return repository.findAllByArchivedDateNotNull();};


    public Project getProjectByName(String name) {
        return repository.findByProjectName(name);
    }

    public void removeProjectByName(String name) {
        repository.deleteProjectByProjectName(name);
    }

}
