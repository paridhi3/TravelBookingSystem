package com.travel.travelbookingsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.travelbookingsystem.entity.TransportAvailability;
import com.travel.travelbookingsystem.repository.TransportAvailabilityRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransportAvailabilityService {

    private final TransportAvailabilityRepository transportAvailabilityRepository;

    public TransportAvailabilityService(TransportAvailabilityRepository transportAvailabilityRepository) {
        this.transportAvailabilityRepository = transportAvailabilityRepository;
    }
    
    public TransportAvailability addTransportAvailability(TransportAvailability availability) {
        return transportAvailabilityRepository.save(availability);
    }

    public List<TransportAvailability> getAllAvailability() {
        return transportAvailabilityRepository.findAll();
    }

    public Optional<TransportAvailability> getAvailabilityByTransportType(String transportType) {
        return transportAvailabilityRepository.findByTransportType(transportType);
    }
    
    public Optional<TransportAvailability> getAvailabilityByTransportId(Long transportId) {
        return transportAvailabilityRepository.findByTransportId(transportId);
    }
    
    public Optional<TransportAvailability> getAvailabilityByTravelDate(LocalDate travelDate) {
        return transportAvailabilityRepository.findByTravelDate(travelDate);
    }
    
    public Optional<TransportAvailability> getAvailability(Long transportId, String transportType, LocalDate travelDate) {
        return transportAvailabilityRepository.findByTransportIdAndTransportTypeAndTravelDate(
                transportId, transportType, travelDate);
    }

    @Transactional
    public boolean reduceAvailableSeats(Long transportId, String transportType, LocalDate travelDate) {
        int rowsUpdated = transportAvailabilityRepository.reduceAvailableSeats(transportId, transportType, travelDate);
        return rowsUpdated > 0;  // Returns true if seats were successfully reduced
    }
}

