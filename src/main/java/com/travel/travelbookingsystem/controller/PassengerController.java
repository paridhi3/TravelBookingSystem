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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")  // Allow requests from any origin
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Constructor-based injection
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    // Register a new passenger
    @PostMapping("/register")
    public ResponseEntity<String> registerPassenger(@RequestBody Passenger passenger) {
        // Check if the email is already registered
        if (passengerService.getPassengerByEmail(passenger.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }

        // Encode the password before saving to the database
        passenger.setPassword(passwordEncoder.encode(passenger.getPassword()));

        // Save passenger
        passengerService.registerPassenger(passenger);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Passenger Successfully Registered!");
    }


    // Login Passenger
//    @PostMapping("/req/login")
//    public ResponseEntity<String> loginPassenger(@RequestBody Passenger passenger) {
//        Passenger existingPassenger = passengerService.getPassengerByEmail(passenger.getEmail());
//        
//        if (existingPassenger == null || passenger.getPassword() == null || 
//            !passwordEncoder.matches(passenger.getPassword(), existingPassenger.getPassword())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//
//        // Authenticate the user (optional if not using Spring Security)
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(passenger.getEmail(), passenger.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return ResponseEntity.ok("Login Successful for " + existingPassenger.getFull_name() + "!");
//    }
    
    @PostMapping("/req/login")
    public ResponseEntity<String> loginPassenger(@RequestBody Passenger passenger) {
        Passenger existingPassenger = passengerService.getPassengerByEmail(passenger.getEmail());

        if (existingPassenger == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        // Debug logs
        System.out.println("Entered Password: " + passenger.getPassword());
        System.out.println("Stored Hashed Password: " + existingPassenger.getPassword());
        System.out.println("Password Matches: " + passwordEncoder.matches(passenger.getPassword(), existingPassenger.getPassword()));
        System.out.println("---------------------------------------------------------------------------------------------------");
        String hashed = passwordEncoder.encode("hello");
        System.out.println("Manually Hashed: " + hashed);
        System.out.println("Matches: " + passwordEncoder.matches("hello", hashed));
        System.out.println("---------------------------------------------------------------------------------------------------");
        String storedHash = "$2a$10$TbPJ8wZA3kHWCBqJxssqLOpvrNQaqoPoGdGNoSn7eeO.WHeGMzfuK"; // from DB
        String enteredPassword = "hello";
        System.out.println("Matches Manually: " + passwordEncoder.matches(enteredPassword, storedHash));



        if (!passwordEncoder.matches(passenger.getPassword(), existingPassenger.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        return ResponseEntity.ok("Login Successful for " + existingPassenger.getFull_name() + "!");
    }



    // Retrieve all passengers
    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        try {
            List<Passenger> passengers = passengerService.getAllPassengers();
            return ResponseEntity.ok(passengers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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

    // Delete a passenger by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassenger(@PathVariable Long id) {
        try {
            if (!passengerService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Passenger not found");
            }
            
            passengerService.deletePassenger(id);
            return ResponseEntity.ok("Passenger deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting passenger: " + e.getMessage());
        }
    }
}
