package com.eggplant.emoji.controller;

import com.eggplant.emoji.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("isAnonymous()")
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String index(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Invalid username or password.");

        if (logout != null)
            model.addAttribute("message", "Successfully logged out.");

        return "login";
    }
}