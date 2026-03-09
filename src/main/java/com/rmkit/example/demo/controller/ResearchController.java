package com.rmkit.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResearchController {

    @GetMapping("/research")
    public String research(Model model) {
        return "research"; 
    }
}