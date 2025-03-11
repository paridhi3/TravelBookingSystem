package com.travel.travelbookingsystem.service;

import com.travel.travelbookingsystem.entity.Passenger;
import com.travel.travelbookingsystem.repository.PassengerRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
//import java.util.Optional;

/**
 * PassengerService Methods:
 * 1. getAllPassengers() -> List<Passenger>
 * 2. getPassengerById(long id) -> Passenger
 * 3. addPassenger(Passenger passenger) -> Passenger
 * 4. deletePassenger(long id) -> void
 * 5. getPassengerByEmail(String email) -> Passenger
 */

@Service
public class PassengerService implements UserDetailsService {
    private final PassengerRepository passengerRepository;
    
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Passenger passenger = passengerRepository.findByEmail(email);
        if (passenger == null) {
            throw new UsernameNotFoundException("Passenger not found with email: " + email);
        }
        return User.withUsername(passenger.getEmail())
                   .password(passenger.getPassword())
                   .build();
    }

    // Optional: method for registering passengers
    public Passenger registerPassenger(Passenger passenger) {
        passenger.setPassword(passwordEncoder().encode(passenger.getPassword()));
        System.out.println("Passenger "+passenger.getFull_name()+" successfully registered!");
        return passengerRepository.save(passenger);
    }

    // Encoder for passwords
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
