package com.eggplant.emoji.controller;


import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.entities.User;
import com.eggplant.emoji.service.ProjectService;
import com.eggplant.emoji.service.UserService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    /**
     * GET request that returns the student view
     * 
     * @return student view
     */
    @GetMapping("/student")
    @PreAuthorize("hasAuthority('STUDENT')")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User student = userService.getUserByEmail(authentication.getName());
        if (student != null) {
            Project project = student.getProject();
            model.addAttribute("name", student.getFullName());
            model.addAttribute("id", Integer.toString(student.getMemberId()));
            model.addAttribute("email", student.getEmail());
            model.addAttribute("project", project);
            if (project != null) {
                String otherStudents = "";
                for (User otherStudent : project.getStudents()) {
                    if (otherStudent != student) {
                        otherStudents += otherStudent.getFirstName() + " " + otherStudent.getLastName() + ", ";
                    }
                }
                if (!otherStudents.equals("")) {
                    model.addAttribute("otherStudents", otherStudents.substring(0, otherStudents.length() - 2));
                }
            }
        }
        return "student";
    }

    /**
     * GET request that tries to make the student join a project
     * 
     * @return student view
     */
    @GetMapping("/joinproject")
    public String getJoinProject(Model model, @RequestParam("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User student = userService.getUserByEmail(authentication.getName());
        if (student.getProject() == null) {
            Project project = projectService.getProjectByID(id);
            project.addStudent(student);
            projectService.updateProject(project);
        }
        return "redirect:/student";
    }
}
