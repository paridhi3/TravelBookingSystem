package com.travel.travelbookingsystem.controller;

import com.travel.travelbookingsystem.entity.Train;
import com.travel.travelbookingsystem.service.TrainService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
@RequestMapping("/api/trains")
public class TrainController {

    private final TrainService trainService;

    // Constructor-based injection
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    // Retrieve all trains
    @GetMapping
    public ResponseEntity<?> getAllTrains() {
        try {
            List<Train> trains = trainService.getAllTrains();
            return ResponseEntity.ok(trains);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching trains: " + e.getMessage());
        }
    }

    // Retrieve a train by ID
    @GetMapping("/{trainId}")
    public ResponseEntity<?> getTrainById(@PathVariable long trainId) {
        try {
            Train train = trainService.getTrainById(trainId);
            return train != null ? ResponseEntity.ok(train) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Train not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching train: " + e.getMessage());
        }
    }

    // Add a new train
    @PostMapping
    public ResponseEntity<?> addTrain(@RequestBody Train train) {
        try {
            trainService.saveTrain(train);
            return ResponseEntity.status(HttpStatus.CREATED).body("Train added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding train: " + e.getMessage());
        }
    }

    // Update train details
    @PutMapping("/{trainId}")
    public ResponseEntity<?> updateTrain(@PathVariable long trainId, @RequestBody Train train) {
        try {
            train.setTrainId(trainId);
            trainService.updateTrain(train);
            return ResponseEntity.ok("Train details updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating train: " + e.getMessage());
        }
    }

    // Delete a train by ID
    @DeleteMapping("/{trainId}")
    public ResponseEntity<?> deleteTrain(@PathVariable long trainId) {
        try {
            trainService.deleteTrainById(trainId);
            return ResponseEntity.ok("Train deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting train: " + e.getMessage());
        }
    }

    // Find trains by source and destination
    @GetMapping("/route")
    public ResponseEntity<?> getTrainsByRoute(@RequestParam String source, @RequestParam String destination) {
        try {
            List<Train> trains = trainService.getTrainsBySourceAndDestination(source, destination);
            return !trains.isEmpty() ? ResponseEntity.ok(trains) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No trains found for this route");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching trains by route: " + e.getMessage());
        }
    }

    // Find trains within a price range
    @GetMapping("/price-range")
    public ResponseEntity<?> getTrainsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        try {
            List<Train> trains = trainService.getTrainsByPriceRange(minPrice, maxPrice);
            return !trains.isEmpty() ? ResponseEntity.ok(trains) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No trains found in this price range");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching trains by price range: " + e.getMessage());
        }
    }
}
