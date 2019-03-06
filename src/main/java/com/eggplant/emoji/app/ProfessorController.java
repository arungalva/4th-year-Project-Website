package com.eggplant.emoji.app;

import com.eggplant.emoji.entities.Project;
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

    public final EntityManager entityManager;

    public ProfessorController(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-test");
        this.entityManager = emf.createEntityManager();
    }

    @GetMapping("/professor")
    public String index(){
        return "professor";
    }

    @GetMapping("/addproject")
    public String getAddProject(Model model){
        model.addAttribute("project", new Project());
        return "addproject";
    }

    @PostMapping("/addproject")
    @Transactional
    public String addProject(@ModelAttribute Project project, Model model){
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(project);
        tx.commit();

        Query q = entityManager.createQuery("SELECT p FROM Project p");
        @SuppressWarnings("unchecked")
        List<Project> results = q.getResultList();
        model.addAttribute("projects",results);

        System.out.println(results);
        return "professor";
    }
}
