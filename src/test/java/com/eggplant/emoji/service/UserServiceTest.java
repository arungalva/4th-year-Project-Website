package com.eggplant.emoji.service;

import com.eggplant.emoji.entities.Program;
import com.eggplant.emoji.entities.Role;
import com.eggplant.emoji.entities.User;
import com.eggplant.emoji.repository.UserRepository;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService service;

    @Autowired
    UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User u;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testPasswordBeingEncrypted(){

        String password = "TestUserPassword123";
        Long id = new Long(1);
        u = new User();
        u.setId(id);
        u.setFirstName("Arun");
        u.setLastName("Galva");
        u.setEmail("arungalva@cmail.carleton.ca");
        u.setMemberId(100976147);
        u.setRole(Role.STUDENT.toString());
        u.setPassword(password);

        service.createAccount(u);


        // check if we didn't store the raw password
        User savedUser = repo.findByEmail("arungalva@cmail.carleton.ca");
        assertNotEquals(savedUser.getPassword(), password);
        assertTrue(passwordEncoder.matches(password, savedUser.getPassword()));

        repo.delete(savedUser);
    }


    @Test
    public void testSaveUser(){

        String password = "TestUserPassword123";
        String email = "arungalva@cmail.carleton.ca";
        u = new User();
        u.setFirstName("Arun");
        u.setLastName("Galva");
        u.setEmail(email);
        u.setMemberId(100976147);
        u.setRole(Role.STUDENT.toString());
        u.setPassword(password);

        String hashedPassword = passwordEncoder.encode(password);
        service.createAccount(u);

        User savedUser = repo.findByEmail(email);
        assertEquals(savedUser.getFirstName(), u.getFirstName());
        assertEquals(savedUser.getLastName(), u.getLastName());
        assertEquals(savedUser.getEmail(), u.getEmail());
        assertEquals(savedUser.getRole(), u.getRole());
        assertEquals(savedUser.getEmail(), u.getEmail());
        assertEquals(savedUser.getMemberId(), u.getMemberId());

        repo.delete(savedUser);

    }

    @Test
    public void testGetAllRoles() {
        Role[] actualAllRoles = service.getAllRoles();
        Role[] expectedAllRoles = Role.values();

        assertArrayEquals(actualAllRoles, expectedAllRoles);
    }

    @Test
    public void testGetAllPrograms() {
        Program[] actualAllPrograms = service.getAllPrograms();
        Program[] expectedAllPrograms = Program.values();

        assertArrayEquals(actualAllPrograms, expectedAllPrograms);
    }

    @Test
    public void testUniqueEmailExceptionRaised() {
        exception.expect(org.springframework.dao.DataIntegrityViolationException.class);
        u = new User();
        u.setFirstName("Arun");
        u.setLastName("Galva");
        u.setEmail("arungalva@cmail.carleton.ca");
        u.setMemberId(100976147);
        u.setRole(Role.STUDENT.toString());
        u.setPassword("Testpassword");
        service.createAccount(u);

        User duplicateEmailuser = new User();
        duplicateEmailuser.setFirstName("Test");
        duplicateEmailuser.setLastName("User");
        duplicateEmailuser.setEmail("arungalva@cmail.carleton.ca");
        duplicateEmailuser.setMemberId(100976197);
        duplicateEmailuser.setRole(Role.STUDENT.toString());
        duplicateEmailuser.setPassword("random password");

        try {
            service.createAccount(duplicateEmailuser);
        } catch (Exception e) {
            repo.delete(u);
            throw e;
        }


    }

    @After
    public void tearDown(){
        if(u != null) {
            repo.delete(u);
        }
    }

}
