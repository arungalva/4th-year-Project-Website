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
        model.addAttribute("projects", getAllNonArchivedProjects());
        return "projects";
    }

    /**
     * Returns all the projects in the database that are not archived
     * @return list of all the projects in the database that are not archived
     */
    private List<Project> getAllNonArchivedProjects(){
        Query q = entityManager.createQuery("SELECT p FROM Project p WHERE p.archivedDate = NULL");
        @SuppressWarnings("unchecked")
        List<Project> results = q.getResultList();
        return results;
    }
}
