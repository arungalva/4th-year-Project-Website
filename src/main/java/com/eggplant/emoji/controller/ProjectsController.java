package com.eggplant.emoji.controller;

import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProjectsController {

    @Autowired
    private ProjectService projectService;

    public ProjectsController() {
    }

    /**
     * GET request that returns the projects view
     * @return projects view
     */
    @GetMapping("/projects")
    public String index(Model model){
        List<Project> allProjects = projectService.getAllNonArchivedProjects();
        model.addAttribute("projects",allProjects);
        return "projects";
    }


}
