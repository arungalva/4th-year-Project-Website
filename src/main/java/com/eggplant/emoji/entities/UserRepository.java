package com.eggplant.emoji.entities;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.eggplant.emoji.entities.User.Role;
import com.eggplant.emoji.entities.User.Program;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findById(int id);
    List<User> findByMemberId(int memberId);
    List<User> findByRole(Role role);
    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    List<User> findByEmail(String email);
    List<User> findByProgram(Program program);

    @Modifying
    @Transactional
    void deleteById(int id);
}