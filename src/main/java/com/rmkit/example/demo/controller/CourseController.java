package com.rmkit.example.demo.controller;

import com.rmkit.example.demo.model.CourseInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CourseController {

    @GetMapping("/courses")
    public String courses(Model model) {
        // Source: RMKEC IT - About the Department (official page)
        // https://www.rmkec.ac.in/2023/infotech_eng/about-the-department/
        // - Program: B.Tech – Information Technology
        // - Intake: 120
        // - NBA Accreditation: 2022–2025
        // - Honours specializations & Minor options (listed below)
        CourseInfo info = new CourseInfo(
                "B.Tech – Information Technology",
                120,
                "Accredited by NBA for the period 2022–2025",
                List.of(
                        "Full Stack Engineering",
                        "Artificial Intelligence & Machine Learning",
                        "Data Science",
                        "Media Processing",
                        "Cyber Security",
                        "Fintech & Web 3.0",
                        "Quantum Computing"
                ),
                List.of(
                        "Internet of Things (IoT)",
                        "Robotics & Automation",
                        "Electric Vehicle Technology",
                        "Embedded Systems",
                        "Business Analytics",
                        "Entrepreneurship & Innovation"
                ),
                // Extra highlights you may want to show on the page (concise and student-facing)
                List.of(
                        "Industry-aligned curriculum with strong practicum components",
                        "Dept. started in 1999; vibrant UG programme with modern labs",
                        "Focus on AI, Data, Full Stack, Cloud & Cyber Security skill-building"
                ),
                "https://www.rmkec.ac.in/2023/infotech_eng/about-the-department/"
        );

        model.addAttribute("course", info);
        return "courses"; // resolves to src/main/resources/templates/courses.html
    }
}
