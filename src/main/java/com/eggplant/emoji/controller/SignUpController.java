package com.eggplant.emoji.controller;

import com.eggplant.emoji.DTO.UserDto;
import com.eggplant.emoji.entities.Project;
import com.eggplant.emoji.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SignUpController {

    @Autowired
    UserService userService;

    @GetMapping("/signup")
    public String index(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "signup";
    }

    @PostMapping
    @Transactional
    public ModelAndView signup(@ModelAttribute UserDto user, Model model) {
        userService.createAccount(user);
        return new ModelAndView("redirect:/projects", (ModelMap) model);
    }
}
