package com.eggplant.emoji.configuration;

import com.eggplant.emoji.entities.Program;
import com.eggplant.emoji.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private ProgramService programService;

    @Autowired
    public DataLoader(ProgramService programService) {
        this.programService = programService;
    }

    public void run(ApplicationArguments args) {
        programService.addProgram(new Program("Biomedical Electrical", "BE"));
        programService.addProgram(new Program("Communication Engineering", "CE"));
        programService.addProgram(new Program("Computer Systems Engineering", "CSE"));
        programService.addProgram(new Program("Software Engineering", "SE"));
        programService.addProgram(new Program("Electrical Engineering", "EE"));
    }
}
