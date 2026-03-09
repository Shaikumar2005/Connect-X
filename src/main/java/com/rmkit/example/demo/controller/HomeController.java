package com.rmkit.example.demo.controller;

import com.rmkit.example.demo.model.User;
import com.rmkit.example.demo.service.UserService;

import jakarta.validation.Valid;
// Optional if you want to keep username in session:
// import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    /* --------------------------
       Root → redirect to /login
       -------------------------- */
    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/login";
    }
    
    
    @GetMapping("/staff-login")
    public String staffLoginPage() {
        return "staff-login"; // create templates/staff-login.html
    }

   

@GetMapping("/logout")
public String logout(HttpSession session, RedirectAttributes ra) {
    if (session != null) {
        try {
            session.invalidate();   // clear everything from session
        } catch (IllegalStateException ignored) {
            // session might already be invalid; safe to ignore
        }
    }
    ra.addFlashAttribute("msg", "You have been logged out.");
    return "redirect:/login";
}



    /* -------------
       Login screens
       ------------- */
    @GetMapping("/login")
    public String loginPage(Model model) {
        // Any error message will be added in POST handler
        return "login"; // renders templates/login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        if (userService.validateLogin(username, password)) {
            session.setAttribute("username", username);
            return "redirect:/home";
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    /* -----------------
       Registration flow
       ----------------- */
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register"; // renders templates/register.html
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.register(user);
        } catch (IllegalArgumentException ex) {
            String msg = ex.getMessage() == null ? "" : ex.getMessage().toLowerCase();
            if (msg.contains("username")) {
                bindingResult.rejectValue("username", "exists", ex.getMessage());
            } else if (msg.contains("email")) {
                bindingResult.rejectValue("email", "exists", ex.getMessage());
            } else {
                model.addAttribute("formError", ex.getMessage());
            }
            return "register";
        }

        // Registration ok → show login page with a message
        model.addAttribute("msg", "Registration successful. Please log in.");
        return "login";
    }

    /* -----------
       Home screen
       ----------- */
    @GetMapping("/home")
    public String homePage(Model model
                           // , HttpSession session   // uncomment if using session
                           ) {
        // If you stored username in session at login, you can expose it here:
        // Object username = session.getAttribute("username");
        // model.addAttribute("username", username);

        return "home"; // ✅ renders src/main/resources/templates/home.html
    }
}