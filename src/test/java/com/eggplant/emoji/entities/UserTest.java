package com.eggplant.emoji.entities;

import com.eggplant.emoji.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UserTest {

    @Test
    public void setRole() {
        User getterAndSetterUser = new User();
        String professorRole = Role.PROFESSOR.toString();
        getterAndSetterUser.setRole(professorRole);
        assertEquals(getterAndSetterUser.getRole(),Role.PROFESSOR);
    }
}
