package com.travel.travelbookingsystem.repository;

import com.travel.travelbookingsystem.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> { // JpaRepository provides basic CRUD methods

    // JPA provides findAll() -> Use findAll() to get all passengers
    // JPA provides findById(Long id) -> Use findById() to get a passenger by ID
    // JPA provides save(Passenger passenger) -> Use save() to insert or update a passenger
    // JPA provides deleteById(Long id) -> Use deleteById() to remove a passenger

    // Custom method to find a passenger by Email
    Passenger findByEmail(String email);
    
    
}
