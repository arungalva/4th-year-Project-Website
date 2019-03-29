package com.eggplant.emoji.service;

import com.eggplant.emoji.entities.Program;
import com.eggplant.emoji.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {
    @Autowired
    private ProgramRepository programRepository;

    public List<Program> findAll() { return programRepository.findAllByOrderByIdAsc(); }

    public void addProgram(Program program) {
        if(!programRepository.existsByProgramNameAndProgramCode(program.getProgramName(), program.getProgramCode())){
            programRepository.save(program);
        }
    }

    public void removeProgramByName(String name) { programRepository.deleteProgramByProgramName(name); }
}
