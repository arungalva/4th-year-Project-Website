package com.eggplant.emoji.entities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgramTest {

    Program program;


    @Test
    public void test_program_values(){
        System.out.println(Program.valueOf("BIOMEDICAL_ELECTRICAL"));
        assert true;
    }
}
