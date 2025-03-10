package com.travel.travelbookingsystem.service;

import com.travel.travelbookingsystem.entity.Passenger;
import com.travel.travelbookingsystem.repository.PassengerRepository;
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
public class PassengerService {
    private final PassengerRepository passengerRepository;
    
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
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
