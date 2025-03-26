package com.travel.travelbookingsystem.controller;

import com.travel.travelbookingsystem.entity.Bus;
import com.travel.travelbookingsystem.service.BusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
@RequestMapping("/api/buses")
public class BusController {

    private final BusService busService;

    // Constructor-based injection
    public BusController(BusService busService) {
        this.busService = busService;
    }

    // Retrieve all buses
    @GetMapping
    public ResponseEntity<?> getAllBuses() {
        try {
            List<Bus> buses = busService.getAllBuses();
            return ResponseEntity.ok(buses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving buses: " + e.getMessage());
        }
    }

    // Retrieve a bus by ID
    @GetMapping("/{busId}")
    public ResponseEntity<?> getBusById(@PathVariable long busId) {
        try {
            Bus bus = busService.getBusById(busId);
            return bus != null ? ResponseEntity.ok(bus) 
                               : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bus not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching bus: " + e.getMessage());
        }
    }

    // Retrieve buses by source and destination
    @GetMapping("/route")
    public ResponseEntity<?> getBusesByRoute(@RequestParam String source, @RequestParam String destination) {
        try {
            List<Bus> buses = busService.getBusesBySourceAndDestination(source, destination);
            return buses.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No buses found for the given route")
                                   : ResponseEntity.ok(buses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching buses: " + e.getMessage());
        }
    }

    // Retrieve buses within a price range
    @GetMapping("/price-range")
    public ResponseEntity<?> getBusesByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        try {
            List<Bus> buses = busService.getBusesByPriceRange(minPrice, maxPrice);
            return buses.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No buses found in this price range")
                                   : ResponseEntity.ok(buses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching buses: " + e.getMessage());
        }
    }

    // Add a new bus
    @PostMapping
    public ResponseEntity<?> addBus(@RequestBody Bus bus) {
        try {
            busService.addBus(bus);
            return ResponseEntity.status(HttpStatus.CREATED).body("Bus added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding bus: " + e.getMessage());
        }
    }

    // Update bus details
    @PutMapping("/{busId}")
    public ResponseEntity<?> updateBus(@PathVariable long busId, @RequestBody Bus bus) {
        try {
            bus.setBusId(busId);
            busService.updateBus(bus);
            return ResponseEntity.ok("Bus details updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating bus: " + e.getMessage());
        }
    }

    // Delete a bus by ID
    @DeleteMapping("/{busId}")
    public ResponseEntity<?> deleteBus(@PathVariable long busId) {
        try {
            busService.deleteBusById(busId);
            return ResponseEntity.ok("Bus deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting bus: " + e.getMessage());
        }
    }
}
