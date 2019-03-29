package com.eggplant.emoji.service;

import com.eggplant.emoji.entities.Program;
import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.repository.ProgramRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgramServiceTest {

    @Autowired
    ProgramService programService;

    @Autowired
    ProgramRepository programRepository;

    @Test
    public void test_add_new_program(){
        Program dummyProgram = new Program();
        dummyProgram.setProgramName("Test add new Program");
        dummyProgram.setProgramCode("CODE");

        programService.addProgram(dummyProgram);

        // verify it got saved
        Program p = programRepository.findByProgramName("Test add new Program");
        assertTrue(p.getProgramName().equals(dummyProgram.getProgramName()));
        assertTrue(p.getProgramCode().equals(dummyProgram.getProgramCode()));

        //remove the project
        programRepository.deleteProgramByProgramName(dummyProgram.getProgramName());
    }

    @Test
    public void test_delete_program() {
        Program dummyProgram = new Program();
        dummyProgram.setProgramName("Test delete new Program");

        programService.addProgram(dummyProgram);
        programService.removeProgramByName(dummyProgram.getProgramName());

        assertTrue(programRepository.findByProgramName(dummyProgram.getProgramName()) == null);

    }
}
