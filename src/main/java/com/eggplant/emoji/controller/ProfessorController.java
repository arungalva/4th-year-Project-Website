package com.eggplant.emoji.controller;

import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.*;
import java.util.List;

@Controller
public class ProfessorController {

    @Autowired
    private ProjectService projectService;

    /**
     * GET request that returns the professor view
     * @return professor view
     */
    @GetMapping("/professor")
    public String index(Model model){
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects",projects);
        return "professor";
    }

    /**
     * GET request that returns the addproject view used to add a new project
     * @param model model used to hold the new object to be created
     * @return addproject view used to create a new project
     */
    @GetMapping("/project/add")
    public String getAddProject(Model model){
        model.addAttribute("project", new Project());
        return "addproject";
    }

    /**
     * Received POST requests and adds the received project object to the database
     * @param project new project object to be added to DB
     * @param model model used to send the list of projects to the view
     * @return the professor view to display all the projects
     */
    @PostMapping("/project/add")
    @Transactional
    public String addProject(@ModelAttribute Project project, Model model){

        projectService.addProject(project);
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects",projects);
        return "professor";
    }
}