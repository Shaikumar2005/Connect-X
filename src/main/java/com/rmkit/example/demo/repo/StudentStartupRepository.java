package com.rmkit.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rmkit.example.demo.model.StudentStartup;

public interface StudentStartupRepository extends JpaRepository<StudentStartup, Long> {
}