package com.eggplant.emoji.entities;

import org.junit.After;
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
        defaultUser.setMemberId(User.DEFAULT_MEMBER_ID);

        // Connect to the DB through the EntityManagerFactory using details found in persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-test");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(defaultUser);
        tx.commit();

        Query q = em.createQuery("SELECT u FROM User u WHERE u.memberId = " + User.DEFAULT_MEMBER_ID);
        @SuppressWarnings("unchecked")
        List<User> results = q.getResultList();
        assertEquals(1, results.size());
        assertEquals(User.DEFAULT_FIRST_NAME, results.get(0).getFirstName());
        assertEquals(User.DEFAULT_LAST_NAME, results.get(0).getLastName());
        assertEquals(User.DEFAULT_MEMBER_ID, results.get(0).getMemberId());
        assertEquals(User.DEFAULT_EMAIL, results.get(0).getEmail());
        assertEquals(User.Role.STUDENT, results.get(0).getRole());
        assertEquals(User.Program.SOFTWARE, results.get(0).getProgram());

        tx.begin();
        em.remove(defaultUser);
        tx.commit();

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

        Query q = em.createQuery("SELECT p FROM Project p WHERE p.projectName = '"+ Project.DEFAULT_PROJECT_NAME +"'");
        @SuppressWarnings("unchecked")
        List<Project> results = q.getResultList();
        assertEquals(1, results.size());
        dummyProject = results.get(0);
        assertEquals(Project.DEFAULT_PROJECT_NAME, dummyProject.getProjectName());
        assertEquals(Project.DEFAULT_PROJECT_DESCRIPTION, dummyProject.getDescription());
        assertEquals(4, dummyProject.getStudents().size());

        List<String> studentFirstNames = dummyProject.getStudents().stream().map((student) -> student.getFirstName()).collect(Collectors.toList());
        assertTrue(studentFirstNames.contains("Arun"));
        assertTrue(studentFirstNames.contains("Blessing"));
        assertTrue(studentFirstNames.contains("Shasthra"));
        assertTrue(studentFirstNames.contains("Sean"));

        tx.begin();
        em.remove(student1);
        em.remove(student2);
        em.remove(student3);
        em.remove(student4);
        em.remove(dummyProject);
        tx.commit();

        // Close the connection
        em.close();
        emf.close();
    }

}