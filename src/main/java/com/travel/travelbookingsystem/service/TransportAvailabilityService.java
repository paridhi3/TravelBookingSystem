package com.travel.travelbookingsystem.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.travelbookingsystem.entity.TransportAvailability;
import com.travel.travelbookingsystem.repository.TransportAvailabilityRepository;

import jakarta.annotation.PostConstruct;

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
    
    @PostConstruct // Runs at startup
    public void updateTransportAvailability() {
        System.out.println("Running transport availability update...");
        transportAvailabilityRepository.deletePastRecords();
        transportAvailabilityRepository.deleteDepartedTransports();
        transportAvailabilityRepository.insertNextDayRecords();
        System.out.println("Transport availability updated.");
    }

//    @Scheduled(cron = "0 */30 * * * ?") // every 30m
//    @Scheduled(cron = "0 0 0 * * ?") // runs at midnight every day
//    @Scheduled(fixedDelay = 60000) // Runs every 60 seconds after previous execution completes
//    @Scheduled(fixedRate = 60000) // Runs every 60 seconds
//    @Scheduled(cron = "*/5 * * * * ?") // runs every 5s
    @Scheduled(cron = "0 0 * * * ?") // Runs every hour
    @Transactional
    public void scheduledUpdate() {
        updateTransportAvailability();
    }
    

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

