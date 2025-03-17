package com.travel.travelbookingsystem.controller;

import com.travel.travelbookingsystem.entity.Flight;
import com.travel.travelbookingsystem.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    // Constructor-based injection
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    // Retrieve all flights
    @GetMapping
    public ResponseEntity<?> getAllFlights() {
        try {
            List<Flight> flights = flightService.getAllFlights();
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving flights: " + e.getMessage());
        }
    }

    // Retrieve flights by source and destination
    @GetMapping("/route")
    public ResponseEntity<?> getFlightsByRoute(@RequestParam String source, @RequestParam String destination) {
        try {
            List<Flight> flights = flightService.getFlightsBySourceAndDestination(source, destination);
            return flights.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No flights found for the given route")
                                     : ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching flights: " + e.getMessage());
        }
    }

    // Add a new flight
    @PostMapping
    public ResponseEntity<?> addFlight(@RequestBody Flight flight) {
        try {
            flightService.addFlight(flight);
            return ResponseEntity.status(HttpStatus.CREATED).body("Flight added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding flight: " + e.getMessage());
        }
    }

    // Delete a flight by ID
    @DeleteMapping("/{flightId}")
    public ResponseEntity<?> deleteFlight(@PathVariable long flightId) {
        try {
            flightService.deleteFlightById(flightId);
            return ResponseEntity.ok("Flight deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting flight: " + e.getMessage());
        }
    }
}
