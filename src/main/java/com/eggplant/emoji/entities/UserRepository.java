package com.eggplant.emoji.entities;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import User.Role;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findById(int id);
    List<User> findByMemberId(int member_id);
    List<User> findByRole(Role role);
    List<User> findByFirstName(String first_name);
    List<User> findByLastName(String last_name);
    List<User> findByEmail(String email);

    @Modifying
    @Transactional
    void deleteById(int id);
}