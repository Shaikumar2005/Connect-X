package com.rmkit.example.demo.service;

import com.rmkit.example.demo.model.StudentStartup;
import com.rmkit.example.demo.repo.StudentStartupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentStartupService {

    @Autowired
    private StudentStartupRepository repo;

    public void saveStartup(StudentStartup startup) {
        repo.save(startup);
    }

    public List<StudentStartup> getAllIdeas() {
        return repo.findAll();
    }

    public StudentStartup getIdeaById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void updateStartup(StudentStartup idea) {
        repo.save(idea);  // JPA auto-updates by ID
    }
}