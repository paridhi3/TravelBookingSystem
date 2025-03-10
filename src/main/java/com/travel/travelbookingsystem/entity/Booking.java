package com.travel.travelbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;  // Foreign key reference to UserEntity

    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type", nullable = false)
    private TransportType transportType;

    @Column(name = "transport_id", nullable = false)
    private long transportId;
    
    @Column(name = "seat_number", length = 10, nullable = false)
    private String seatNumber;

    @Column(name = "travel_date", nullable = false)
    private LocalDate travelDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false)
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING; // Default value;
    
    @Column(name = "booking_date", nullable = false, updatable = false)
    private LocalDateTime bookingDate;

    @PrePersist
    protected void onCreate() {
        if (bookingDate == null) {
            bookingDate = LocalDateTime.now();
        }
    }

	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public TransportType getTransportType() {
		return transportType;
	}

	public void setTransportType(TransportType transportType) {
		this.transportType = transportType;
	}

	public long getTransportId() {
		return transportId;
	}

	public void setTransportId(long transportId) {
		this.transportId = transportId;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public LocalDate getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(LocalDate travelDate) {
		this.travelDate = travelDate;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", passenger=" + passenger + ", transportType=" + transportType
				+ ", transportId=" + transportId + ", seatNumber=" + seatNumber + ", travelDate=" + travelDate
				+ ", bookingStatus=" + bookingStatus + ", paymentStatus=" + paymentStatus + ", bookingDate="
				+ bookingDate + ", getBookingId()=" + getBookingId() + ", getPassenger()=" + getPassenger()
				+ ", getTransportType()=" + getTransportType() + ", getTransportId()=" + getTransportId()
				+ ", getSeatNumber()=" + getSeatNumber() + ", getTravelDate()=" + getTravelDate()
				+ ", getBookingStatus()=" + getBookingStatus() + ", getPaymentStatus()=" + getPaymentStatus()
				+ ", getBookingDate()=" + getBookingDate() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
    
}






