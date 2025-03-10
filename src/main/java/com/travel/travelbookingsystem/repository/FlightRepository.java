package com.travel.travelbookingsystem.repository;

import com.travel.travelbookingsystem.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    // ✅ Already provided by JpaRepository:
    // - save(Flight flight) → Saves/updates a flight
    // - findAll() → Retrieves all flights
    // - findById(Long flightId) → Finds a flight by ID
    // - deleteById(Long flightId) → Deletes a flight by ID

    // ❌ Custom Query Methods (Not provided by default)

    // Find flights by source and destination
    List<Flight> findBySourceAndDestination(String source, String destination);

    // Find flights within a price range
    List<Flight> findByPriceBetween(double minPrice, double maxPrice);

    // ✅ Update available seats
    @Modifying
    @Transactional
    @Query("UPDATE Flight f SET f.availableSeats = :newSeats WHERE f.flightId = :flightId")
    int updateAvailableSeats(Long flightId, int newSeats);
}
