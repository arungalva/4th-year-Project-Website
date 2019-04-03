package com.eggplant.emoji.service;

import com.eggplant.emoji.entities.Program;
import com.eggplant.emoji.entities.Role;
import com.eggplant.emoji.entities.User;
import com.eggplant.emoji.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean createAccount(User account) {
        if (repo.findByEmail(account.getEmail()) != null) {
            return false;
        }
        account.setEmail(account.getEmail().toLowerCase());
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        repo.save(account);
        return true;
    }

    public User getUserByEmail(String email) {
        return repo.findByEmail(email);
    }

    public void deleteByEmail(String email) {
        User u = repo.findByEmail(email);
        repo.delete(u);
    }

    public Role[] getAllRoles(){
        return Role.values();
    }

    public Program[] getAllPrograms() {
        return Program.values();
    }

    public User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return this.getUserByEmail(authentication.getName());
    }
}
