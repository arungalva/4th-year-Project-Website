package com.eggplant.emoji.repository;

import com.eggplant.emoji.entities.Program;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProgramRepository extends CrudRepository<Program, Long> {
    boolean existsByProgramNameAndProgramCode(String programName, String programCode);
    List<Program> findAllByOrderByIdAsc();
    Program findByProgramName(String programName);

    @Modifying
    @Transactional
    void deleteProgramByProgramName(String programName);
}
