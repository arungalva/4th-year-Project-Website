package com.eggplant.emoji.entities;

import com.eggplant.emoji.service.UserService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UserTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void setRole() {
        User getterAndSetterUser = new User();
        String professorRole = Role.PROFESSOR.toString();
        getterAndSetterUser.setRole(professorRole);
        assertEquals(getterAndSetterUser.getRole(),Role.PROFESSOR);
    }


    @Test
    public void testMemberIdMinValueInvalid(){
        User u = new User();
        u.setFirstName("Arun");
        u.setLastName("Galva");
        u.setEmail("arungalva@cmail.carleton.ca");
        u.setMemberId(99999999);
        u.setRole(Role.STUDENT.toString());
        u.setPassword("testpassword123");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate( u );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Number must be a 9 digit number",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testMemeberIdMaxValueInvalid(){
        User u = new User();
        u.setFirstName("Arun");
        u.setLastName("Galva");
        u.setEmail("arungalva@cmail.carleton.ca");
        u.setMemberId(2000000000);
        u.setRole(Role.STUDENT.toString());
        u.setPassword("testpassword123");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate( u );
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                "Number must be a 9 digit number",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testMemeberIdValid(){
        User u = new User();
        u.setFirstName("Arun");
        u.setLastName("Galva");
        u.setEmail("arungalva@cmail.carleton.ca");
        u.setMemberId(100976147);
        u.setRole(Role.STUDENT.toString());
        u.setPassword("testpassword123");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate( u );
        assertEquals( 0, constraintViolations.size() );
    }
}
