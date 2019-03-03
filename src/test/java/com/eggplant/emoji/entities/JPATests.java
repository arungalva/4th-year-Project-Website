package com.eggplant.emoji.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

public class JPATests {

    @Test
    public void testDefaultUserJPA() {
        User defaultUser = new User();

        // Connect to the DB through the EntityManagerFactory using details found in persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-test");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(defaultUser);
        tx.commit();

        Query q = em.createQuery("SELECT u FROM User u");
        @SuppressWarnings("unchecked")
        List<User> results = q.getResultList();

        assertEquals(results.size(), 1);
        assertEquals(results.get(0).getFirstName(), User.DEFAULT_FIRST_NAME);
        assertEquals(results.get(0).getLastName(), User.DEFAULT_LAST_NAME);
        assertEquals(results.get(0).getMemberId(), User.DEFAULT_MEMBER_ID);
        assertEquals(results.get(0).getEmail(), User.DEFAULT_EMAIL);
        assertEquals(results.get(0).getRole(), User.Role.STUDENT);
        assertEquals(results.get(0).getProgram(), User.Program.SOFTWARE);

        // Close the connection
        em.close();
        emf.close();
    }

    @Test
    public void testProjectJPA() {
        Project dummyProject = new Project();
        User student1 = new User(100100100, User.Role.STUDENT, "Arun", "Galva", "mynamearun@carleton.ca", User.Program.SOFTWARE);
        User student2 = new User(100100101, User.Role.STUDENT, "Blessing", "Omotayo", "titilope@carleton.ca", User.Program.SOFTWARE);
        User student3 = new User(100100102, User.Role.STUDENT, "Shasthra", "Longlastname", "shasthra@carleton.ca", User.Program.SOFTWARE);
        User student4 = new User(100100103, User.Role.STUDENT, "Sean", "Tohidi", "sean@carleton.ca", User.Program.SOFTWARE);
        dummyProject.addStudent(student1);
        dummyProject.addStudent(student2);
        dummyProject.addStudent(student3);
        dummyProject.addStudent(student4);

        // Connect to the DB through the EntityManagerFactory using details found in persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-test");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(dummyProject);
        tx.commit();

        Query q = em.createQuery("SELECT p FROM Project p");
        @SuppressWarnings("unchecked")
        List<Project> results = q.getResultList();

        assertEquals(results.size(), 1);
        dummyProject = results.get(0);
        assertEquals(dummyProject.getProjectName(), Project.DEFAULT_PROJECT_NAME);
        assertEquals(dummyProject.getDescription(), Project.DEFAULT_PROJECT_DESCRIPTION);
        assertEquals(dummyProject.getStudents().size(), 4);

        List<String> studentFirstNames = dummyProject.getStudents().stream().map((student) -> student.getFirstName()).collect(Collectors.toList());
        assertTrue(studentFirstNames.contains("Arun"));
        assertTrue(studentFirstNames.contains("Blessing"));
        assertTrue(studentFirstNames.contains("Shasthra"));
        assertTrue(studentFirstNames.contains("Sean"));

        // Close the connection
        em.close();
        emf.close();
    }

}