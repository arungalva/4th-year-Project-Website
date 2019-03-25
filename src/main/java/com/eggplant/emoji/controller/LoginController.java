package com.eggplant.emoji.controller;

import com.eggplant.emoji.entities.LoginForm;
import com.eggplant.emoji.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/userLogin")
    public String index(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);
        return "userLogin";
    }

    @PostMapping("/userLogin")
    @Transactional
        public String login(@Valid LoginForm loginForm, BindingResult result, Model model) {
        if(result.hasErrors()){
            model.addAttribute("loginForm", loginForm);
            return "userLogin";
        }
        else if(!userService.AuthenticateUser(loginForm.getEmail(), loginForm.getPassword())) {
            model.addAttribute("authError", "Email or password is not correct");
            return "userLogin";
        }
        return "redirect:/projects";
    }
}
