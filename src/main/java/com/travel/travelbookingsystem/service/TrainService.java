package com.travel.travelbookingsystem.service;

import com.travel.travelbookingsystem.entity.train;
import com.travel.travelbookingsystem.entity.Train;
import com.travel.travelbookingsystem.repository.TrainRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//import java.util.Optional;
import java.util.Optional;

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
    @Autowired
    private TransportAvailabilityService transportAvailabilityService;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public Train addTrain(Train train) {
        Train savedTrain = trainRepository.save(train);

        // Pass totalSeats from Train entity
        transportAvailabilityService.addTransportAvailability(savedTrain.getTrainId(), "TRAIN", savedTrain.getTotalSeats());

        return savedTrain;
    }

//    // Save or update a tin
//    public Train saveTrain(Train trai {
//        return trainRepository.save(train); // JPA handles both insert and upte
//    }

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

    // Delete a train by ID
    public void deletetrainById(long trainId) {
        Optional<Train> trainOptional = trainRepository.findById(trainId);
        
        if (trainOptional.isPresent()) {
            Train train = trainOptional.get();  // Unwrapping Optional
            transportAvailabilityService.deleteTransportAvailability(train.getTrainId(), "TRAIN");
            trainRepository.deleteById(trainId);
        } else {
            throw new EntityNotFoundException("Train with ID " + trainId + " not found");
        }
    }

    // Retrieve trains by source and destination
    public List<Train> getTrainsBySourceAndDestination(String source, String destination) {
        return trainRepository.findBySourceAndDestination(source, destination); // Custom JPA method
    }

    // Retrieve trains within a specific price range
    public List<Train> getTrainsByPriceRange(double minPrice, double maxPrice) {
        return trainRepository.findByPriceBetween(minPrice, maxPrice); // Custom JPA method
    }
    
    public boolean existsById(Long id) {
        return trainRepository.existsById(id);
    }
}
