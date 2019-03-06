package com.eggplant.emoji.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

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

    @GetMapping("/projects")
    public String projects() {
        return "projects";
    }

}
