package com.travel.travelbookingsystem.repository;

import com.travel.travelbookingsystem.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    // Find flights by source and destination
    List<Flight> findBySourceAndDestination(String source, String destination);

    // Find flights within a price range
    List<Flight> findByPriceBetween(double minPrice, double maxPrice);


}
