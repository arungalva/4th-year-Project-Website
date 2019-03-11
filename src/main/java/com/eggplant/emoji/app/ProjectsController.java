package com.eggplant.emoji.app;

import com.eggplant.emoji.entities.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Controller
public class ProjectsController {

    public final EntityManager entityManager;

    public ProjectsController(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-test");
        this.entityManager = emf.createEntityManager();
    }

    /**
     * GET request that returns the projects view
     * @return projects view
     */
    @GetMapping("/projects")
    public String index(Model model){
        model.addAttribute("projects", getAllProjects());
        return "projects";
    }

    /**
     * Returns all the projects in the database
     * @return list of all the projects in the database
     */
    private List<Project> getAllProjects(){
        Query q = entityManager.createQuery("SELECT p FROM Project p");
        @SuppressWarnings("unchecked")
        List<Project> results = q.getResultList();
        return results;
    }
}
