package com.travel.travelbookingsystem.controller;

import com.travel.travelbookingsystem.entity.TransportAvailability;
import com.travel.travelbookingsystem.service.TransportAvailabilityService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
    public List<TransportAvailability> getAvailabilityByTransportType(@RequestParam String transportType) {
        return transportAvailabilityService.getAvailabilityByTransportType(transportType);
    }

    @GetMapping("/by-id")
    public List<TransportAvailability> getAvailabilityByTransportId(@RequestParam Long transportId) {
        return transportAvailabilityService.getAvailabilityByTransportId(transportId);
    }

    @GetMapping("/by-date")
    public List<TransportAvailability> getAvailabilityByTravelDate(@RequestParam LocalDate travelDate) {
        return transportAvailabilityService.getAvailabilityByTravelDate(travelDate);
    }

    @GetMapping("/check")
    public List<TransportAvailability> getAvailability(
            @RequestParam Long transportId,
            @RequestParam String transportType,
            @RequestParam LocalDate travelDate) {
        return transportAvailabilityService.getAvailability(transportId, transportType, travelDate);
    }

    @PostMapping("/reduce-seats")
    public boolean reduceAvailableSeats(
            @RequestParam Long transportId,
            @RequestParam String transportType,
            @RequestParam LocalDate travelDate) {
        return transportAvailabilityService.reduceAvailableSeats(transportId, transportType, travelDate);
    }
}
