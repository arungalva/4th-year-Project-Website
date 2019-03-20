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
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
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
        List<Project> allProjects = projectService.findAll();

        List<Project> allNonArchivedProjects = getAllNonArchiedProjects(allProjects);
        model.addAttribute("projects",allNonArchivedProjects);
        return "professor";
    }

    /**
     * GET request that returns the archived projects
     * @return archived Project view
     */
    @GetMapping("/archivedProjects")
    public String archivedProjects(Model model){
        List<Project> allProjects = projectService.findAll();

        List<Project> allArchivedProjects = getAllArchiedProjects(allProjects);
        model.addAttribute("projects",allArchivedProjects);
        return "archivedProjects";
    }

    /**
     * Received POST requests and calls archivedProject() on the project that was clicked
     * @param request HttpServletRequest request from the input field
     * @param model model used to send the list of projects to the view
     * @return the professor view to display all the non archived projects
     */
    @PostMapping("/archive")
    @Transactional
    public RedirectView archiveProject(HttpServletRequest request, Model model){
        Long id = Long.parseLong(request.getParameter("id"));

        Project existingProject = projectService.findById(id);
        existingProject.archiveProject();

        projectService.updateProject(existingProject);

        List<Project> allProjects = projectService.findAll();

        List<Project> allNonArchivedProjects = getAllNonArchiedProjects(allProjects);
        model.addAttribute("projects",allNonArchivedProjects);
        return new RedirectView("/professor");
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
        List<Project> allProjects = projectService.findAll();

        List<Project> allNonArchivedProjects = getAllNonArchiedProjects(allProjects);
        model.addAttribute("projects",allNonArchivedProjects);
        return "professor";
    }

    public List<Project> getAllNonArchiedProjects(List<Project> allProjects) {
        for(int i = 0; i < allProjects.size(); i++) {
            if(allProjects.get(i).getArchivedDate() != null) {
                allProjects.remove(allProjects.get(i));
            }
        }

        return allProjects;
    }

    public List<Project> getAllArchiedProjects(List<Project> allProjects) {
        for(int i = 0; i < allProjects.size(); i++) {
            if(allProjects.get(i).getArchivedDate() == null) {
                allProjects.remove(allProjects.get(i));
            }
        }

        return allProjects;
    }
}
