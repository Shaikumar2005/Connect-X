package com.rmkit.example.demo.controller;

import com.rmkit.example.demo.model.User;
import com.rmkit.example.demo.service.UserService;

import jakarta.validation.Valid;
// Optional if you want to keep username in session:
// import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
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
            model.addAttribute("formError", ex.getMessage());
            return "register";
        }

        model.addAttribute("msg", "Registration successful! Please log in.");
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
    
    @GetMapping("/Gallery")
    private List<String> getImages(String type) {
        List<String> images = new ArrayList<>();

        String folderPath = "src/main/resources/static/Gallery/" + type;

        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    images.add(file.getName());
                }
            }
        }

        return images;
    }
    
    // EVENTS
    @GetMapping("/gallery/events")
    public String showEvents(Model model) {
        model.addAttribute("images", getImages("events"));
        model.addAttribute("type", "events");
        return "gallery";
    }

    // CONFERENCES
    @GetMapping("/gallery/Achievers")
    public String showConferences(Model model) {
        model.addAttribute("images", getImages("Achievers"));
        model.addAttribute("type", "Achievers");
        return "gallery";
    }

    // FACULTY
    @GetMapping("/gallery/faculty")
    public String showFaculty(Model model) {
        model.addAttribute("images", getImages("faculty"));
        model.addAttribute("type", "faculty");
        return "gallery";
    }

    // DEFAULT PAGE
    @GetMapping("/gallery")
    public String defaultGallery() {
        return "redirect:/gallery/events";
    }
    
    @GetMapping("/clubs")
    public String clubsPage() {
        return "clubs";
    }

    @GetMapping("/clubs/{club}")
    public String clubDetails(@PathVariable String club, Model model) {

        String folderPath = "src/main/resources/static/clubs/" + club;

        File folder = new File(folderPath);
        List<String> images = new ArrayList<>();

        if (folder.exists()) {
            for (File file : folder.listFiles()) {
                images.add(file.getName());
            }
        }

        model.addAttribute("images", images);
        model.addAttribute("clubKey", club);

        // Dynamic Name & Description
        model.addAttribute("clubName", club.toUpperCase() + " CLUB");
        model.addAttribute("description", "This is " + club + " club where students actively participate in events and activities.");

        return "club-details";
    }

    

@GetMapping("/forms")
public String formsPage(Model model) {
    model.addAttribute("title", "Download Forms");
    return "forms";
}

    

@GetMapping("/timetable")
public String timetablePage(Model model) {
    return "timetable";  // loads timetable.html
}





    

}

