package com.travel.travelbookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.travel.travelbookingsystem.entity.Admin;
import com.travel.travelbookingsystem.repository.AdminRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AdminService {
	
	private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Ensure an admin user exists on startup
    @PostConstruct
    public void initializeAdmin() {
        String adminEmail = "admin@example.com";
        if (adminRepository.findByEmail(adminEmail) == null) {
            Admin admin = new Admin();
            admin.setFullName("Admin User");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("admin123")); // Hashed password
            adminRepository.save(admin);
            System.out.println("✅ Default admin user created.");
        } else {
            System.out.println("⚠️ Admin user already exists. Skipping initialization.");
        }
    }

    // Save Admin with Encoded Password
    public String saveAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
        return "Admin saved successfully!";
    }
    
    public Admin getAdminByEmail(String email) {
    	return adminRepository.findByEmail(email);
    }

    // Authenticate Admin
    public boolean authenticateAdmin(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        System.out.println("Login admin: "+admin);

        if (admin != null) { // Check if the passenger exists
            // Debugging logs (remove in production)
            System.out.println("Entered Password: " + password);
            System.out.println("Stored Hashed Password from database: " + admin.getPassword());
            System.out.println("Password Matches: " + passwordEncoder.matches(password, admin.getPassword()));

            // Verify password using BCrypt
            return passwordEncoder.matches(password, admin.getPassword());
        }

        return false; // admin not found
    }


}
