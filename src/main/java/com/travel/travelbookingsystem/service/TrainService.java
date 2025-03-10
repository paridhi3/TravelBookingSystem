package com.travel.travelbookingsystem.service;

import com.travel.travelbookingsystem.entity.Train;
import com.travel.travelbookingsystem.repository.TrainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//import java.util.Optional;

/*
 * TrainService Methods:
 * 1. saveTrain(Train train) -> Train
 * 2. getAllTrains() -> List<Train>
 * 3. getTrainById(long trainId) -> Train
 * 4. updateTrain(Train train) -> Train
 * 5. reduceAvailableSeats(Long trainId) -> boolean
 * 6. deleteTrainById(long trainId) -> void
 * 7. getTrainsBySourceAndDestination(String source, String destination) -> List<Train>
 * 8. getTrainsByPriceRange(double minPrice, double maxPrice) -> List<Train>
 */

@Service
public class TrainService {
    private final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    // Save or update a train
    public Train saveTrain(Train train) {
        return trainRepository.save(train); // JPA handles both insert and update
    }

    // Retrieve all trains
    public List<Train> getAllTrains() {
        return trainRepository.findAll(); // JPA method
    }

    // Retrieve a train by ID
    public Train getTrainById(long trainId) {
        return trainRepository.findById(trainId).orElse(null); // Avoids NullPointerException
    }

    // Update train details (save also works for update)
    public Train updateTrain(Train train) {
        return trainRepository.save(train); // JPA save() updates if ID exists
    }

//    // Reduce available seats when a booking is made
//    @Transactional
//    public boolean reduceAvailableSeats(Long trainId) {
//        Optional<Train> optionalTrain = trainRepository.findById(trainId);
//        if (optionalTrain.isEmpty()) {
//            System.out.println("Train not found with ID: " + trainId);
//            return false;
//        }
//
//        Train train = optionalTrain.get();
//        if (train.getAvailableSeats() <= 0) {
//            System.out.println("No available seats for Train ID: " + trainId);
//            return false;
//        }
//
//        System.out.println("Before Update: Available Seats = " + train.getAvailableSeats());
//
//        int rowsUpdated = trainRepository.updateAvailableSeats(trainId, train.getAvailableSeats());
//        if (rowsUpdated > 0) {
//            System.out.println("After Update: Available Seats = " + (train.getAvailableSeats() - 1));
//            return true;
//        }
//        
//        return false;
//    }
    
    // use either method
    @Transactional
    public boolean reduceAvailableSeats(Long trainId) {
        int rowsUpdated = trainRepository.updateAvailableSeats(trainId, 1);
        return rowsUpdated > 0; // Returns true if update was successful
    }

    // Delete a train by ID
    public void deleteTrainById(long trainId) {
        trainRepository.deleteById(trainId); // JPA method
    }

    // Retrieve trains by source and destination
    public List<Train> getTrainsBySourceAndDestination(String source, String destination) {
        return trainRepository.findBySourceAndDestination(source, destination); // Custom JPA method
    }

    // Retrieve trains within a specific price range
    public List<Train> getTrainsByPriceRange(double minPrice, double maxPrice) {
        return trainRepository.findByPriceBetween(minPrice, maxPrice); // Custom JPA method
    }
}
