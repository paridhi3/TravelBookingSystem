package com.travel.travelbookingsystem.repository;

import com.travel.travelbookingsystem.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    // ✅ Already provided by JPA:
    // - save(Bus bus)           → Saves/updates a bus
    // - findAll()               → Retrieves all buses
    // - findById(Long busId)    → Finds a bus by ID
    // - deleteById(Long busId)  → Deletes a bus by ID

    // ❌ Custom Query Methods (not directly provided by JPA)

    // Find buses by source and destination
    List<Bus> findBySourceAndDestination(String source, String destination);

    // Find buses within a price range
    List<Bus> findByPriceBetween(double minPrice, double maxPrice);

    // ✅ Update available seats
//    @Modifying
//    @Transactional
//    @Query("UPDATE Bus b SET b.availableSeats = :newSeats WHERE b.busId = :busId")
//    int updateAvailableSeats(Long busId, int newSeats);
    
//    @Modifying
//    @Transactional
//    @Query("UPDATE Bus b SET b.availableSeats = b.availableSeats - 1 WHERE b.busId = :busId AND b.availableSeats > 0")
//    int updateAvailableSeats(@Param("busId") Long busId);
}
