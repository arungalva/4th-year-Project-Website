package com.eggplant.emoji.repository;

import com.eggplant.emoji.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findById(int id);
    List<User> findByMemberId(int memberId);
    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    User findByEmail(String email);

    @Modifying
    @Transactional
    void deleteById(Long id);
}