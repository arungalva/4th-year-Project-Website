package com.eggplant.emoji.entities;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

    // Once we implement authentication, we can return a User field of the logged in User
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("DEFAULT AUDITOR Babak");
    }
}