package com.travel.travelbookingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travelbookingsystem.entity.Admin;
import com.travel.travelbookingsystem.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
//@RequiredArgsConstructor
public class AdminController {
	
	private final AdminService adminService;
	
	@Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
	
	// Retrieve a passenger by email (Path Variable)
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getAdminByEmail(@PathVariable String email) { 
        try {
            Admin admin = adminService.getAdminByEmail(email);
            return admin != null ? ResponseEntity.ok(admin)
                                     : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching admin: " + e.getMessage());
        }
    }

    // Endpoint to Authenticate Admin
	@PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Admin admin) {	
		
		System.out.println("Received email in AdminController: " + admin.getEmail());
	    System.out.println("Received password in AdminController: " + admin.getPassword());
		
		boolean isAuthenticated = adminService.authenticateAdmin(admin.getEmail(), admin.getPassword());

        if (isAuthenticated) {
        	Admin existingAdmin = adminService.getAdminByEmail(admin.getEmail());
        	System.out.println("Login Successful for " + existingAdmin.getFullName() + "!");
            return ResponseEntity.ok(existingAdmin);
        } else {
            return ResponseEntity.status(401).body("{\"message\": \"Invalid email or password!\"}");
        }
    }


}