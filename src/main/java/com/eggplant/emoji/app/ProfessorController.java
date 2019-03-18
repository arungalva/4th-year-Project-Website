package com.eggplant.emoji.app;

import com.eggplant.emoji.entities.Project;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfessorController {

    public final EntityManager entityManager;

    public ProfessorController(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-test");
        this.entityManager = emf.createEntityManager();
    }

    /**
     * GET request that returns the professor view
     * @return professor view
     */
    @GetMapping("/professor")
    public String index(Model model){
        model.addAttribute("projects",getAllNonArchivedProjects());
        return "professor";
    }

    /**
     * GET request that returns the archived projects
     * @return archived Project view
     */
    @GetMapping("/archivedProjects")
    public String archivedProjects(Model model){

        model.addAttribute("projects",getAllArchivedProjects());
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

        Query q = entityManager.createQuery("SELECT p FROM Project p WHERE p.id = :id");
        q.setParameter("id", id);
        @SuppressWarnings("unchecked")
        List<Project> results = q.getResultList();
        Project project = results.get(0);

        project.archiveProject();

        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(project);
        tx.commit();

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
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(project);
        tx.commit();

        model.addAttribute("projects",getAllNonArchivedProjects());
        return "professor";
    }

    private List<Project> getAllArchivedProjects(){
        Query q = entityManager.createQuery("SELECT p FROM Project p WHERE p.archivedDate != NULL");
        @SuppressWarnings("unchecked")
        List<Project> results = q.getResultList();
        return results;
    }

    private List<Project> getAllNonArchivedProjects(){
        Query q = entityManager.createQuery("SELECT p FROM Project p WHERE p.archivedDate = NULL");
        @SuppressWarnings("unchecked")
        List<Project> results = q.getResultList();
        return results;
    }
}
