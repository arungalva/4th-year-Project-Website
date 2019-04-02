package com.eggplant.emoji.controller;

import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('STUDENT')")
    public String index(Model model){
        return "student";
    }
}
