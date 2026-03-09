package com.rmkit.example.demo.controller;

import com.rmkit.example.demo.model.StaffView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class FacultyStaticController {
	

@GetMapping("/about-it")
    public String aboutDepartment(Model model) {
        return "about-it";
    }


    @GetMapping("/faculty")
    public String faculty(Model model) {
        List<StaffView> staff = getFacultyFromOfficialPageSnapshot(); // synchronized with live page

        // Normalize designations to a canonical set
        Function<String, String> normalize = FacultyStaticController::normalizeDesignation;

        // Seniority order (higher first)
        List<String> order = List.of(
                "Professor & Head",
                "Professor & Dean",
                "Professor",
                "Associate Professor (Gr I)",
                "Associate Professor (Gr II)",
                "Associate Professor",
                "Assistant Professor (Gr II)",
                "Assistant Professor"
        );

        Map<String, Integer> weight = new HashMap<>();
        for (int i = 0; i < order.size(); i++) weight.put(order.get(i), i);

        // Sort by canonical designation weight, then by name
        List<StaffView> sorted = staff.stream()
                .sorted(Comparator
                        .comparingInt((StaffView s) -> weight.getOrDefault(normalize.apply(s.getDesignation()), 999))
                        .thenComparing(StaffView::getFullName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        // Group into sections in the final seniority order
        LinkedHashMap<String, List<StaffView>> sections = new LinkedHashMap<>();
        for (String key : order) {
            List<StaffView> bucket = sorted.stream()
                    .filter(s -> normalize.apply(s.getDesignation()).equals(key))
                    .collect(Collectors.toList());
            if (!bucket.isEmpty()) sections.put(key, bucket);
        }

        // Add any designations not in our order mapping at the end (rare)
        List<StaffView> others = sorted.stream()
                .filter(s -> !sections.values().stream().flatMap(Collection::stream).collect(Collectors.toSet()).contains(s))
                .collect(Collectors.toList());
        if (!others.isEmpty()) {
            sections.put("Other", others);
        }

        model.addAttribute("sections", sections);
        model.addAttribute("totalStaff", staff.size());
        return "faculty";
    }

    private static String normalizeDesignation(String raw) {
        if (raw == null) return "Other";

        // Convert any accidental "&amp;" to "&" then normalize spacing
        String d = raw.replace("&amp;", "&").replaceAll("\\s+", " ").trim();
        String lo = d.toLowerCase();

        if (lo.contains("professor & head")) return "Professor & Head";
        if (lo.contains("professor & dean")) return "Professor & Dean";

        if (lo.startsWith("associate professor")) {
            if (d.matches("(?i).*gr\\s*\\(i\\).*")) return "Associate Professor (Gr I)";
            if (d.matches("(?i).*(grade-?ii|gr\\s*ii).*")) return "Associate Professor (Gr II)";
            return "Associate Professor";
        }
        if (lo.startsWith("assistant professor")) {
            if (d.matches("(?i).*(grade-?ii|gr\\s*ii).*")) return "Assistant Professor (Gr II)";
            return "Assistant Professor";
        }
        if (lo.startsWith("professor")) return "Professor";

        return d; // fallback
    }

    /**
     * Snapshot from: https://www.rmkec.ac.in/2023/infotech_eng/faculty/
     * (If the official page updates, update this list accordingly.)
     */
    private List<StaffView> getFacultyFromOfficialPageSnapshot() {
        String dept = "Information Technology";
        List<StaffView> list = new ArrayList<>();

        // ---- As shown on the official page (names / designations / emails / images) ----
        list.add(new StaffView("Dr. M. Sheerin Banu", "Professor & Head", dept, "hod.it@rmkec.ac.in", "/images/faculty/sheerin-banu.png"));
        list.add(new StaffView("Dr. K. Manivannan", "Professor & Dean", dept, null, "/images/faculty/k-manivannan.png"));

        list.add(new StaffView("Dr. K. Saravanan", "Professor", dept, "ksn.it@rmkec.ac.in", "/images/faculty/k-saravanan.png"));
        list.add(new StaffView("Dr. G. S. Anandha Mala", "Professor", dept, "gsa.it@rmkec.ac.in", "/images/faculty/gs-anandha-mala.png"));
        list.add(new StaffView("Dr. Abitha Kumari", "Professor", dept, "dak.it@rmkec.ac.in", "/images/faculty/abitha-kumari.png"));

        list.add(new StaffView("Dr. S. Selvakanmani", "Associate Professor", dept, "ssk.it@rmkec.ac.in", "/images/faculty/s-selvakanmani.png"));
        list.add(new StaffView("Dr. A. Vijayaraj", "Associate Professor", dept, "avj.it@rmkec.ac.in", "/images/faculty/a-vijayaraj.png"));
        list.add(new StaffView("Dr. A. Anna Lakshmi", "Associate Professor", dept, "aal.it@rmkec.ac.in", "/images/faculty/a-anna-lakshmi.png"));
        list.add(new StaffView("Dr. R. Rajitha Jasmine", "Associate Professor", dept, "rrj.it@rmkec.ac.in", "/images/faculty/r-rajitha-jasmine.png"));
        list.add(new StaffView("Dr. T. Mahalingam", "Associate Professor", dept, "tmm.it@rmkec.ac.in", "/images/faculty/t-mahalingam.png"));
        list.add(new StaffView("Dr. M. Shanthi", "Associate Professor", dept, null, "/images/faculty/m-shanthi.png"));

        list.add(new StaffView("Dr. R. Poornima", "Assistant Professor Grade-II", dept, "rpa.it@rmkec.ac.in", "/images/faculty/r-poornima.png"));
        list.add(new StaffView("Dr. A. Anu Monisha", "Associate Professor Grade-II", dept, null, "/images/faculty/a-anu-monisha.png"));
        list.add(new StaffView("Mr. L. Vinoth Kumar", "Associate Professor  Gr (I)", dept, "vk.it@rmkec.ac.in", "/images/faculty/l-vinoth-kumar.png"));
        list.add(new StaffView("Mr. N. Shanmugam", "Assistant Professor", dept, "shan.cc@rmkec.ac.in", "/images/faculty/n-shanmugam.png"));
        list.add(new StaffView("Ms. M. Rekha", "Assistant Professor", dept, "mra.it@rmkec.ac.in", "/images/faculty/m-rekha.png"));
        list.add(new StaffView("Ms. M. Kanniga Parameshwari", "Assistant Professor", dept, "mkp.it@rmkec.ac.in", "/images/faculty/m-kanniga-parameshwari.png"));
        list.add(new StaffView("Ms. S. Shobana", "Associate Professor  Gr (I)", dept, "ssh.it@rmkec.ac.in", "/images/faculty/s-shobana.png"));
        list.add(new StaffView("Ms. K. Selvi", "Assistant Professor", dept, "ksi.it@rmkec.ac.in", "/images/faculty/k-selvi.png"));
        list.add(new StaffView("Ms. J. Divya", "Assistant Professor Grade-II", dept, "daj.csd@rmkec.ac.in", "/images/faculty/j-divya.png"));
        list.add(new StaffView("Ms. M. Sindhu", "Assistant Professor", dept, null, "/images/faculty/m-sindhu.png"));
        list.add(new StaffView("Ms. S. Nandhini", "Assistant Professor", dept, "sni.it@rmkec.ac.in", "/images/faculty/s-nandhini.png"));
        list.add(new StaffView("Ms. T. Akila", "Assistant Professor", dept, "taa.it@rmkec.ac.in", "/images/faculty/t-akila.png"));
        list.add(new StaffView("Mr. S. Nagarajan", "Assistant Professor", dept, "snn.it@rmkec.ac.in", "/images/faculty/s-nagarajan.png"));
        list.add(new StaffView("Ms. R. Renugadevi", "Assistant Professor", dept, "rri.it@rmkec.ac.in", "/images/faculty/r-renugadevi.png"));

        return list;
    }
}