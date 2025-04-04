package com.travel.travelbookingsystem.repository;

import com.travel.travelbookingsystem.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> { 

    // Custom method to find a passenger by Email
    Passenger findByEmail(String email);
    
    
}
