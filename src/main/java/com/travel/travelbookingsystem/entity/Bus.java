package com.travel.travelbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Entity
@Table(name = "bus")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long busId;

    @Column(name = "bus_name", nullable = false)
    private String busName;
    
    @Column(name = "source", nullable = false)
    private String source;
    
    @Column(name = "destination", nullable = false)
    private String destination;
    
    @Column(name = "departure_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime departureTime;
    
    @Column(name = "arrival_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime arrivalTime;
    
    @Column(name = "total_seats", nullable = false)
    private int totalSeats;
    
//    @Column(name = "available_seats", nullable = false)
//    private int availableSeats;
    
    @Column(name = "price", nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "bus_class", nullable = false)
    private BusClass busClass;

	public long getBusId() {
		return busId;
	}

	public void setBusId(long busId) {
		this.busId = busId;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

//	public int getAvailableSeats() {
//		return availableSeats;
//	}
//
//	public void setAvailableSeats(int availableSeats) {
//		this.availableSeats = availableSeats;
//	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public BusClass getBusClass() {
		return busClass;
	}

	public void setBusClass(BusClass busClass) {
		this.busClass = busClass;
	}
    
    
}
