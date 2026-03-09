package com.rmkit.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "staffs",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_staffs_username", columnNames = "username"),
        @UniqueConstraint(name = "uk_staffs_email", columnNames = "email")
    }
)
public class StaffEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic identity (used by your controller/templates)
    @Column(nullable = false, length = 40)
    private String username;

    @Column(nullable = false, length = 120)
    private String email;

    /**
     * NOTE:
     *  - Kept nullable=true so you can create faculty cards without setting a password.
     *  - If you later use this same row for login, you MUST set a proper bcrypt hash here.
     */
    @Column(name = "password_hash", length = 120, nullable = true)
    private String passwordHash;

    // Faculty profile fields
    @Column(name = "full_name", length = 120)
    private String fullName;

    @Column(length = 100)
    private String designation;

    @Column(length = 100)
    private String department;

    @Column(length = 20)
    private String phone;

    // Stored file name under /uploads/staff/
    @Column(name = "photo_filename", length = 255)
    private String photoFilename;

    // ---- Constructors ----
    public StaffEntity() {}

    // ---- Getters / Setters ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPhotoFilename() { return photoFilename; }
    public void setPhotoFilename(String photoFilename) { this.photoFilename = photoFilename; }
}