package com.travel.travelbookingsystem.service;

import com.travel.travelbookingsystem.entity.Flight;
import com.travel.travelbookingsystem.repository.FlightRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * FlightService Methods:
 * 1. saveFlight(Flight flight) -> Flight
 * 2. getAllFlights() -> List<Flight>
 * 3. getFlightById(long flightId) -> Flight
 * 4. updateFlight(Flight flight) -> Flight
 * 5. reduceAvailableSeats(Long flightId) -> boolean
 * 6. deleteFlightById(long flightId) -> void
 * 7. getFlightsBySourceAndDestination(String source, String destination) -> List<Flight>
 * 8. getFlightsByPriceRange(double minPrice, double maxPrice) -> List<Flight>
 */

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    @Autowired
    private TransportAvailabilityService transportAvailabilityService;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight addFlight(Flight flight) {
        Flight savedFlight = flightRepository.save(flight);

        // Pass totalSeats from Flight entity
        transportAvailabilityService.addTransportAvailability(savedFlight.getFlightId(), "FLIGHT", savedFlight.getTotalSeats());

        return savedFlight;
    }

    // Retrieve all flights (JPA provides findAll())
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // Retrieve a flight by ID (JPA provides findById())
    public Flight getFlightById(long flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }

    // Update flight details (Handled by save())
    public Flight updateFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    // Delete a flight by ID (JPA provides deleteById())
    public void deleteFlightById(long flightId) {
        Optional<Flight> flightOptional = flightRepository.findById(flightId);
        
        if (flightOptional.isPresent()) {
            Flight flight = flightOptional.get();  // Unwrapping Optional
            transportAvailabilityService.deleteTransportAvailability(flight.getFlightId(), "FLIGHT");
            flightRepository.deleteById(flightId);
        } else {
            throw new EntityNotFoundException("Flight with ID " + flightId + " not found");
        }
    }


    // Retrieve flights by source and destination (JPA supports derived query methods)
    public List<Flight> getFlightsBySourceAndDestination(String source, String destination) {
        return flightRepository.findBySourceAndDestination(source, destination);
    }

    // Retrieve flights within a specific price range (JPA supports derived query methods)
    public List<Flight> getFlightsByPriceRange(double minPrice, double maxPrice) {
        return flightRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public boolean existsById(Long id) {
        return flightRepository.existsById(id);
    }
}
