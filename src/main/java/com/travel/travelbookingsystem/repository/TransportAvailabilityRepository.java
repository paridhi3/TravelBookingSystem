package com.travel.travelbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.travel.travelbookingsystem.entity.TransportAvailability;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TransportAvailabilityRepository extends JpaRepository<TransportAvailability, Long> {
	
//	List<TransportAvailability> findAll();

    // Fetch availability for a specific transport type, ID, and date
    Optional<TransportAvailability> findByTransportIdAndTransportTypeAndTravelDate(Long transportId, String transportType, LocalDate travelDate);
    
    // Fetch availability for a specific transport type
    Optional<TransportAvailability> findByTransportType(String transportType);
    
    // Fetch availability for a specific transport ID
    Optional<TransportAvailability> findByTransportId(Long transportId);
    
    // Fetch availability for a specific date
    Optional<TransportAvailability> findByTravelDate(LocalDate travelDate);

    // Reduce available seats when a booking is made
    @Modifying
    @Transactional
    @Query("UPDATE TransportAvailability ta SET ta.availableSeats = ta.availableSeats - 1 " +
           "WHERE ta.transportId = :transportId AND ta.transportType = :transportType " +
           "AND ta.travelDate = :travelDate AND ta.availableSeats > 0")
    int reduceAvailableSeats(Long transportId, String transportType, LocalDate travelDate);
}

