package com.travel.travelbookingsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.travelbookingsystem.entity.TransportAvailability;
import com.travel.travelbookingsystem.repository.TransportAvailabilityRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransportAvailabilityService {

    private final TransportAvailabilityRepository transportAvailabilityRepository;

    public TransportAvailabilityService(TransportAvailabilityRepository transportAvailabilityRepository) {
        this.transportAvailabilityRepository = transportAvailabilityRepository;
    }
    
    @Transactional
    public void addTransportAvailability(Long transportId, String transportType, int totalSeats) {
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 7; i++) {  // Create entries for today + next 6 days
            LocalDate travelDate = today.plusDays(i);

            boolean exists = transportAvailabilityRepository.existsByTransportIdAndTransportTypeAndTravelDate(
                    transportId, transportType, travelDate);

            if (!exists) {
                TransportAvailability availability = new TransportAvailability();
                availability.setTransportId(transportId);
                availability.setTransportType(transportType);
                availability.setTravelDate(travelDate);
                availability.setAvailableSeats(totalSeats); // Provided by transport service

                transportAvailabilityRepository.save(availability);
            }
        }
    }
    
//    public TransportAvailability addTransportAvailability(TransportAvailability availability) {
//        return transportAvailabilityRepository.save(availability);
//    }

    public List<TransportAvailability> getAllAvailability() {
        return transportAvailabilityRepository.findAll();
    }

    public List<TransportAvailability> getAvailabilityByTransportType(String transportType) {
        return transportAvailabilityRepository.findByTransportType(transportType);
    }
    
    public List<TransportAvailability> getAvailabilityByTransportId(Long transportId) {
        return transportAvailabilityRepository.findByTransportId(transportId);
    }
    
    public List<TransportAvailability> getAvailabilityByTravelDate(LocalDate travelDate) {
        return transportAvailabilityRepository.findByTravelDate(travelDate);
    }
    
    public List<TransportAvailability> getAvailability(Long transportId, String transportType, LocalDate travelDate) {
        return transportAvailabilityRepository.findByTransportIdAndTransportTypeAndTravelDate(
                transportId, transportType, travelDate);
    }

    @Transactional
    public boolean reduceAvailableSeats(Long transportId, String transportType, LocalDate travelDate) {
        int rowsUpdated = transportAvailabilityRepository.reduceAvailableSeats(transportId, transportType, travelDate);
        return rowsUpdated > 0;  // Returns true if seats were successfully reduced
    }
}

