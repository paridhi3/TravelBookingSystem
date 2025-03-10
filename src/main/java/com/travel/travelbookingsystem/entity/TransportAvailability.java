package com.travel.travelbookingsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

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
        this.travelDate = travelDate;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
