package com.eggplant.emoji.controller;

import com.eggplant.emoji.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ProjectService service;

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/deadlines")
    public String deadlines() {
        return "deadlines";
    }

    @GetMapping("/notices")
    public String notices() {
        return "notices";
    }

    @GetMapping("/accessdenied")
    public String accessdenied() { return "accessdenied"; }

}
