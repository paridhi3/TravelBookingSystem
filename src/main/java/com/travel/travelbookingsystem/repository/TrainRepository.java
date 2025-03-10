package com.travel.travelbookingsystem.repository;

import com.travel.travelbookingsystem.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> { // JpaRepository provides basic CRUD methods

    // JPA provides save(Train train) -> Use save() for inserting and updating
    // JPA provides findAll() -> Use findAll() to get all trains
    // JPA provides findById(Long trainId) -> Use findById() to get a train by ID
    // JPA provides deleteById(Long trainId) -> Use deleteById() to delete a train

    // Custom query to update available seats
    @Modifying
    @Transactional
    @Query("UPDATE Train t SET t.availableSeats = t.availableSeats - :seatChange WHERE t.trainId = :trainId AND t.availableSeats >= :seatChange")
    int updateAvailableSeats(Long trainId, int seatChange);

    // Custom method to find trains by source and destination
    List<Train> findBySourceAndDestination(String source, String destination);

    // Custom method to find trains within a price range
    List<Train> findByPriceBetween(double minPrice, double maxPrice);
}
