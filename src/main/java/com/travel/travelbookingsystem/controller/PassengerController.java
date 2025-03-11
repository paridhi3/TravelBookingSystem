package com.travel.travelbookingsystem.controller;

import com.travel.travelbookingsystem.entity.Passenger;
import com.travel.travelbookingsystem.service.PassengerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")  // Allow requests from any origin
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    // Constructor-based injection
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }
    
    // Register a new passenger
    @PostMapping("/register")
    public String registerPassenger(@ModelAttribute Passenger passenger) {
        // Encode the password before saving to the database
        passengerService.registerPassenger(passenger);
        return "Passenger Successfully Registered!";  // Redirect to login page after successful registration
    }

    // Handle login (this is handled by Spring Security, but you can add custom logic if needed)
    @PostMapping("/req/login")
    public String loginPassenger(@ModelAttribute Passenger passenger) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(passenger.getEmail(), passenger.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "Login Successful!"; 
    }

    // Retrieve all passengers
    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        try {
            List<Passenger> passengers = passengerService.getAllPassengers();
            return ResponseEntity.ok(passengers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Retrieve a passenger by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPassenger(@PathVariable Long id) {
        try {
            Passenger passenger = passengerService.getPassengerById(id);
            return passenger != null ? ResponseEntity.ok(passenger)
                                     : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Passenger not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching passenger: " + e.getMessage());
        }
    }
    
//    // Add a new passenger
//    @PostMapping
//    public ResponseEntity<String> createPassenger(@RequestBody Passenger passenger) {
//        try {
//            passengerService.addPassenger(passenger);
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body("Passenger added successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error adding passenger: " + e.getMessage());
//        }
//    }

    // Delete a passenger by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassenger(@PathVariable Long id) {
        try {
            if (!passengerService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Passenger not found");
            }
            
            passengerService.deletePassenger(id); // Since it returns void, just call it
            return ResponseEntity.ok("Passenger deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting passenger: " + e.getMessage());
        }
    }

}
