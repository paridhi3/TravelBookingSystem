package com.travel.travelbookingsystem.repository;

import com.travel.travelbookingsystem.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> { 

    // Custom method to find trains by source and destination
    List<Train> findBySourceAndDestination(String source, String destination);

    // Custom method to find trains within a price range
    List<Train> findByPriceBetween(double minPrice, double maxPrice);
}
