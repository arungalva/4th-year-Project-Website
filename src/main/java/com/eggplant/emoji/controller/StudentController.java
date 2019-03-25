package com.eggplant.emoji.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    /**
     * GET request that returns the student view
     * @return student view
     */
    @GetMapping("/student")
    public String index(Model model){
        return "student";
    }
}
