package com.eggplant.emoji.service;

import com.eggplant.emoji.entities.Program;
import com.eggplant.emoji.entities.Role;
import com.eggplant.emoji.entities.User;
import com.eggplant.emoji.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createAccount(User account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        repo.save(account);
    }

    public Role[] getAllRoles(){
        return Role.values();
    }

    public Program[] getAllPrograms() {
        return Program.values();
    }
}
