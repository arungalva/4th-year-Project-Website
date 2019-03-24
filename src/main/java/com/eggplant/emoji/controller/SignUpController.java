package com.eggplant.emoji.controller;

import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.entities.User;
import com.eggplant.emoji.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class SignUpController {

    @Autowired
    UserService userService;

    @GetMapping("/signup")
    public String index(Model model){
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles", userService.getAllRoles());
        return "signup";
    }

    @PostMapping("/signup")
    @Transactional
    public String signup(@Valid User user, BindingResult result, Model model) {
            if(result.hasErrors()){
            model.addAttribute("user", user);
            model.addAttribute("roles", userService.getAllRoles());
            return "signup";
        }
        userService.createAccount(user);
        return "redirect:/projects";
    }
}
