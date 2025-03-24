package com.travel.travelbookingsystem.repository;

import com.travel.travelbookingsystem.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
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

}
