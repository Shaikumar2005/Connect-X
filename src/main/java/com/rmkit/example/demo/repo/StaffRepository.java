package com.rmkit.example.demo.repo;

import com.rmkit.example.demo.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<StaffEntity, Long> {
    Optional<StaffEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}