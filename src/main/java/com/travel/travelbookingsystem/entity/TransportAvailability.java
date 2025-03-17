package com.travel.travelbookingsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.time.LocalDate;
import java.util.Set;

import com.travel.travelbookingsystem.validation.ValidTravelDate;

@Entity
@Table(name = "transport_availability")
public class TransportAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "transport_id", nullable = false)
    private Long transportId;  // References Flight/Bus/Train ID

    @Column(name = "transport_type", nullable = false, length = 10)
    private String transportType;  // "FLIGHT", "BUS", or "TRAIN"

    @Column(name = "travel_date", nullable = false)
    @ValidTravelDate
    private LocalDate travelDate;


    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    // Constructors
    public TransportAvailability() {}

    public TransportAvailability(Long transportId, String transportType, LocalDate travelDate, int availableSeats) {
        this.transportId = transportId;
        this.transportType = transportType;
        this.travelDate = travelDate;
        this.availableSeats = availableSeats;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        LocalDate today = LocalDate.now();
        LocalDate maxAllowedDate = today.plusDays(6);

        if (travelDate.isBefore(today) || travelDate.isAfter(maxAllowedDate)) {
            throw new IllegalArgumentException("Travel date must be between today and the next 6 days.");
        }
        this.travelDate = travelDate;
    }


    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
    
    @PrePersist
    @PreUpdate
    private void validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<TransportAvailability>> violations = validator.validate(this);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations.iterator().next().getMessage());
        }
    }
    
}
