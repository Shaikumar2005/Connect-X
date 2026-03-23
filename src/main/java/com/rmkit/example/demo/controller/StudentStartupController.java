package com.rmkit.example.demo.controller;

import com.rmkit.example.demo.model.StudentStartup;
import com.rmkit.example.demo.service.StudentStartupService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentStartupController {

    @Autowired
    private StudentStartupService service;

    // --- SHOW SUBMISSION FORM ---
    @GetMapping("/startup/new")
    public String showStartupForm(Model model) {
        model.addAttribute("startup", new StudentStartup());
        return "startup_form";
    }

    // --- SAVE STARTUP IDEA ---
    @PostMapping("/startup/save")
    public String saveStartup(@ModelAttribute StudentStartup startup, HttpSession session) {

        String loggedUser = (String) session.getAttribute("username");

        // Always store who created the idea
        startup.setCreatedBy(loggedUser);

        service.saveStartup(startup);
        return "redirect:/startup/list";
    }

    // --- SHOW ALL IDEAS ---
    @GetMapping("/startup/list")
    public String listStartups(Model model) {
        model.addAttribute("ideas", service.getAllIdeas());
        return "startup_list";
    }

    // --- QUICK LINK ---
    @GetMapping("/student")
    public String openStudentPortal(Model model) {
        model.addAttribute("startup", new StudentStartup());
        return "startup_form";
    }

    // --- EDIT FORM (Only creator can edit) ---
    @GetMapping("/startup/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {

        StudentStartup idea = service.getIdeaById(id);
        if (idea == null) {
            return "redirect:/startup/list";
        }

        String loggedUser = (String) session.getAttribute("username");

        // Safe equals check
        if (loggedUser == null || !loggedUser.equals(idea.getCreatedBy())) {
            return "redirect:/startup/list?error=not-authorized";
        }

        model.addAttribute("startup", idea);
        return "startup_edit_form";
    }

    // --- UPDATE IDEA ---
    @PostMapping("/startup/update")
    public String updateIdea(@ModelAttribute("startup") StudentStartup updatedIdea,
                             HttpSession session) {

        StudentStartup existingIdea = service.getIdeaById(updatedIdea.getId());
        String loggedUser = (String) session.getAttribute("username");

        if (existingIdea == null) {
            return "redirect:/startup/list";
        }

        // Safe null-free check
        if (loggedUser == null || !loggedUser.equals(existingIdea.getCreatedBy())) {
            return "redirect:/startup/list?error=not-authorized";
        }

        // Prevent owner change
        updatedIdea.setCreatedBy(existingIdea.getCreatedBy());

        service.updateStartup(updatedIdea);
        return "redirect:/startup/list";
    }
}