package com.rmkit.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ForgotPasswordController {

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        // renders src/main/resources/templates/forgot-password.html
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordSubmit(@RequestParam("email") String email,
                                       Model model) {
        // TODO: lookup user, generate token, send email
        model.addAttribute("sent", true); // or add error if user missing
        return "forgot-password";
    }
}