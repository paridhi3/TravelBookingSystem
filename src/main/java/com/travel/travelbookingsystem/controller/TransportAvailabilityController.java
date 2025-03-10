package com.travel.travelbookingsystem.controller;

import com.travel.travelbookingsystem.entity.TransportAvailability;
import com.travel.travelbookingsystem.service.TransportAvailabilityService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
@RequestMapping("/api/availability")
public class TransportAvailabilityController {

    private final TransportAvailabilityService transportAvailabilityService;

    public TransportAvailabilityController(TransportAvailabilityService transportAvailabilityService) {
        this.transportAvailabilityService = transportAvailabilityService;
    }

    @GetMapping
    public List<TransportAvailability> getAllAvailability() {
        return transportAvailabilityService.getAllAvailability();
    }

    @GetMapping("/by-type")
    public Optional<TransportAvailability> getAvailabilityByTransportType(@RequestParam String transportType) {
        return transportAvailabilityService.getAvailabilityByTransportType(transportType);
    }

    @GetMapping("/by-id")
    public Optional<TransportAvailability> getAvailabilityByTransportId(@RequestParam Long transportId) {
        return transportAvailabilityService.getAvailabilityByTransportId(transportId);
    }

    @GetMapping("/by-date")
    public Optional<TransportAvailability> getAvailabilityByTravelDate(@RequestParam LocalDate travelDate) {
        return transportAvailabilityService.getAvailabilityByTravelDate(travelDate);
    }

    @GetMapping("/check")
    public Optional<TransportAvailability> getAvailability(
            @RequestParam Long transportId,
            @RequestParam String transportType,
            @RequestParam LocalDate travelDate) {
        return transportAvailabilityService.getAvailability(transportId, transportType, travelDate);
    }
    
    @PostMapping
    public ResponseEntity<TransportAvailability> addTransportAvailability(@RequestBody TransportAvailability availability) {
        TransportAvailability savedAvailability = transportAvailabilityService.addTransportAvailability(availability);
        return new ResponseEntity<>(savedAvailability, HttpStatus.CREATED);
    }

    @PostMapping("/reduce-seats")
    public boolean reduceAvailableSeats(
            @RequestParam Long transportId,
            @RequestParam String transportType,
            @RequestParam LocalDate travelDate) {
        return transportAvailabilityService.reduceAvailableSeats(transportId, transportType, travelDate);
    }
}
