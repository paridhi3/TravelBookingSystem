package com.travel.travelbookingsystem.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.travel.travelbookingsystem.entity.Passenger;
import com.travel.travelbookingsystem.repository.PassengerRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PassengerService implements UserDetailsService {
    
    private final PassengerRepository passengerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository, PasswordEncoder passwordEncoder) {
        this.passengerRepository = passengerRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Passenger passenger = passengerRepository.findByEmail(email);
        if (passenger == null) {
            throw new UsernameNotFoundException("Passenger not found with email: " + email);
        }
        return User.withUsername(passenger.getEmail())
                   .password(passenger.getPassword())
                   .roles("USER")  // Add role
                   .build();
    }

//    // Register a new passenger
//    public Passenger registerPassenger(Passenger passenger) {
//    	System.out.println(passenger);
//    	if (passenger.getPassword() == null || passenger.getPassword().isEmpty()) {
//            throw new IllegalArgumentException("Password cannot be null or empty");
//        }
//        passenger.setPassword(passwordEncoder.encode(passenger.getPassword())); // Encrypt password
//        System.out.println("Raw Password at Registration: '" + passenger.getPassword() + "'");
//        System.out.println("Passenger " + passenger.getFull_name() + " successfully registered!");
//        return passengerRepository.save(passenger);
//    }
    
    public Passenger registerPassenger(Passenger passenger) {
        System.out.println("Register passenger: " + passenger);
        System.out.println("Password from database: "+passenger.getPassword());

        if (passenger.getPassword() == null || passenger.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Check if password is already hashed
        if (!passenger.getPassword().startsWith("$2a$")) { // BCrypt hashes start with "$2a$"
            passenger.setPassword(passwordEncoder.encode(passenger.getPassword())); // Encrypt password
            System.out.println("Hashed Password at Registration: '" + passenger.getPassword() + "'");
        } else {
            System.out.println("Warning: Password was already hashed before registration!");
        }

        System.out.println("Passenger " + passenger.getFull_name() + " successfully registered!");
        return passengerRepository.save(passenger);
    }
    
//    public boolean register(UserAuthentication user) {
//        Optional<UserAuthentication> existingUser = userRepository.findByUsername(user.getUsername());
//        if (existingUser.isPresent()) {
//            return false; // Username already exists
//        }
//
//        // Hash the password before saving
//        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
//        user.setPasswordHash(hashedPassword);
//
//        return userRepository.save(user) > 0;
//    }
    
    // Authenticate user (for login)
    public boolean authenticate(String email, String password) {
        Passenger passenger = passengerRepository.findByEmail(email);
        
        System.out.println("Login passenger: "+passenger);

        if (passenger != null) { // Check if the passenger exists
            // Debugging logs (remove in production)
            System.out.println("Entered Password: " + password);
            System.out.println("Stored Hashed Password from database: " + passenger.getPassword());
            System.out.println("Password Matches: " + passwordEncoder.matches(password, passenger.getPassword()));

            // Verify password using BCrypt
            return passwordEncoder.matches(password, passenger.getPassword());
        }

        return false; // User not found
    }





    // Retrieve all passengers
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    // Retrieve a passenger by ID
    public Passenger getPassengerById(long id) {
        return passengerRepository.findById(id).orElse(null);
    }

    // Retrieve a passenger by email
    public Passenger getPassengerByEmail(String email) {
        return passengerRepository.findByEmail(email);
    }

    // Add a new passenger
    public Passenger addPassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    // Delete a passenger by ID
    public void deletePassenger(long id) {
        passengerRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return passengerRepository.existsById(id);
    }
}
