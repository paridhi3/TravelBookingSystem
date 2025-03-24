package com.travel.travelbookingsystem.repository;

import com.travel.travelbookingsystem.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> { // JpaRepository provides basic CRUD methods

    // JPA provides save(Train train) -> Use save() for inserting and updating
    // JPA provides findAll() -> Use findAll() to get all trains
    // JPA provides findById(Long trainId) -> Use findById() to get a train by ID
    // JPA provides deleteById(Long trainId) -> Use deleteById() to delete a train

    // Custom method to find trains by source and destination
    List<Train> findBySourceAndDestination(String source, String destination);

    // Custom method to find trains within a price range
    List<Train> findByPriceBetween(double minPrice, double maxPrice);
}
