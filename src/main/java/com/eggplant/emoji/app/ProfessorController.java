package com.eggplant.emoji.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfessorController {

    @GetMapping("/professor")
    public String index(){
        return "professor";
    }
}
