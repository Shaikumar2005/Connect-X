package com.rmkit.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping // no class-level path, we’ll map each endpoint explicitly
public class PublicationController {

    /**
     * Landing page that shows two options: Students / Faculty.
     * Renders: src/main/resources/templates/publications.html
     */
    @GetMapping("/publications")
    public String publicationsSelector() {
        return "publications";
    }

    /**
     * Students publications page.
     * Renders: src/main/resources/templates/StudentPublications.html
     */
    @GetMapping("/publications/students")
    public String studentPublications() {
        return "StudentPublications";
    }

    /**
     * Faculty publications page.
     * Renders: src/main/resources/templates/FacultyPublications.html
     */
    @GetMapping("/publications/faculty")
    public String facultyPublications() {
        return "FacultyPublications";
    }
}