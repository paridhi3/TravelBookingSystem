package com.travel.travelbookingsystem.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.travelbookingsystem.entity.Bus;
import com.travel.travelbookingsystem.repository.BusRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * BusService Methods:
 * 1. saveBus(Bus bus) -> Bus
 * 2. getAllBuses() -> List<Bus>
 * 3. getBusById(long busId) -> Bus
 * 4. updateBus(Bus bus) -> Bus
 * 5. reduceAvailableSeats(Long busId) -> boolean
 * 6. deleteBusById(long busId) -> void
 * 7. getBusesBySourceAndDestination(String source, String destination) -> List<Bus>
 * 8. getBusesByPriceRange(double minPrice, double maxPrice) -> List<Bus>
 */

@Service
public class BusService {

    private final BusRepository busRepository;
    @Autowired
    private TransportAvailabilityService transportAvailabilityService;

    @Autowired
    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public Bus addBus(Bus bus) {
        Bus savedBus = busRepository.save(bus);

        // Pass totalSeats from Bus entity
        transportAvailabilityService.addTransportAvailability(savedBus.getBusId(), "BUS", savedBus.getTotalSeats());

        return savedBus;
    }

    // Retrieve all buses
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    // Find a bus by its ID
    public Bus getBusById(long busId) {
        return busRepository.findById(busId).orElse(null);
    }

    // Update bus details
    public Bus updateBus(Bus bus) {
        return busRepository.save(bus);
    }

    // Delete a bus by ID
    public void deleteBusById(long busId) {
        Optional<Bus> busOptional = busRepository.findById(busId);
        
        if (busOptional.isPresent()) {
            Bus bus = busOptional.get();  // Unwrapping Optional
            transportAvailabilityService.deleteTransportAvailability(bus.getBusId(), "BUS");
            busRepository.deleteById(busId);
        } else {
            throw new EntityNotFoundException("Bus with ID " + busId + " not found");
        }
    }

    // Retrieve buses by source and destination
    public List<Bus> getBusesBySourceAndDestination(String source, String destination) {
        return busRepository.findBySourceAndDestination(source, destination);
    }

    // Retrieve buses within a price range
    public List<Bus> getBusesByPriceRange(double minPrice, double maxPrice) {
        return busRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    public boolean existsById(Long id) {
        return busRepository.existsById(id);
    }
}
