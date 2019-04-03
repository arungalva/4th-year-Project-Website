package com.eggplant.emoji.controller;

import com.eggplant.emoji.entities.Program;
import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.entities.User;
import com.eggplant.emoji.service.ProjectService;
import com.eggplant.emoji.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.EnumSet;
import java.util.List;

@Controller
public class ProjectsController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    /**
     * GET request that returns the projects view
     * @return projects view
     */
    @GetMapping("/projects")
    public String index(Model model){
        List<Project> allProjects = projectService.getAllNonArchivedProjects();
        model.addAttribute("projects",allProjects);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserByEmail(authentication.getName());
        if (currentUser != null) {
            model.addAttribute("role", currentUser.getRole().toString());
            model.addAttribute("currentProject", currentUser.getProject());
        }
        return "projects";
    }


    /**
     * GET request that returns a project page for the given project id
     * @param projectId id for the project
     * @return Single project view
     */

    @GetMapping("/projects/{id}")
    public String getEditProject(@PathVariable("id") Long projectId, Model model){
        Project existingProject = projectService.findById(projectId);
        model.addAttribute("project", existingProject);
        User currentUser = userService.getLoggedInUser();
        model.addAttribute("currentProject", currentUser.getProject());
        return "viewProject";
    }


}
