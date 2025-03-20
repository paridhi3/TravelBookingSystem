/*
 * BookingService Methods:
 * 1. getAllBookings() -> List<Booking>
 * 2. getBookingById(long bookingId) -> Booking
 * 3. getBookingsByPassengerId(long passengerId) -> List<Booking>
 * 4. getBookingsByTransportType(String transportType) -> List<Booking>
 * 5. createBooking(Booking booking, Long passengerId, Long transportId) -> Booking
 * 6. updateBookingStatus(long bookingId, String status) -> void
 * 7. updatePaymentStatus(long bookingId, String status) -> void
 * 8. deleteBooking(long bookingId) -> void
 */

package com.travel.travelbookingsystem.service;

import com.travel.travelbookingsystem.entity.Booking;
import com.travel.travelbookingsystem.entity.BookingStatus;
import com.travel.travelbookingsystem.entity.Passenger;
import com.travel.travelbookingsystem.entity.PaymentStatus;
import com.travel.travelbookingsystem.entity.TransportType;
import com.travel.travelbookingsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TransportAvailabilityService transportAvailabilityService;
    private final PassengerService passengerService;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          TransportAvailabilityService transportAvailabilityService,
                          PassengerService passengerService) {
        this.bookingRepository = bookingRepository;
        this.transportAvailabilityService = transportAvailabilityService;
        this.passengerService = passengerService;
    }

    // Retrieve all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Retrieve a booking by ID
    public Booking getBookingById(long bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }

    // Retrieve bookings by passenger ID
    public List<Booking> getBookingsByPassengerId(long passengerId) {
        return bookingRepository.findByPassenger_PassengerId(passengerId);
    }

    // Retrieve bookings by transport type
    public List<Booking> getBookingsByTransportType(String transportType) {
        return bookingRepository.findByTransportType(transportType);
    }
    
    public List<Booking> getBookingsByTransportTypeTransportId(TransportType transportType, long transportId, LocalDate travelDate) {
        return bookingRepository.findByTransportTypeAndTransportIdAndTravelDate(transportType, transportId, travelDate);
    }

    public List<String> getBookedSeats(TransportType transportType, long transportId, LocalDate travelDate) {
        List<Booking> bookings = getBookingsByTransportTypeTransportId(transportType, transportId, travelDate);

        // Extract seat numbers from bookings
        return bookings.stream()
                       .map(Booking::getSeatNumber) // Assuming `getSeatNumber()` exists
                       .collect(Collectors.toList());
    }

    // Create Booking
    @Transactional
    public Booking createBooking(Booking booking, Long passengerId, Long transportId) {
    	
    	LocalDate travelDate = booking.getTravelDate();
    	
        // Retrieve passenger
        Passenger passenger = passengerService.getPassengerById(passengerId);
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger not found with ID: " + passengerId);
        }
        
        System.out.println("Booking object: " + booking);
        System.out.println("Transport type: " + booking.getTransportType());
        String transportType = booking.getTransportType().name(); // Extracting transport type

        // Step 1: Reduce available seats for the given transport
        boolean seatsUpdated = transportAvailabilityService.reduceAvailableSeats(transportId, transportType, travelDate);
        if (!seatsUpdated) {
            throw new IllegalStateException("No available seats for this booking");
        }

        // Step 2: Set booking details
        booking.setPassenger(passenger);
        booking.setTransportId(transportId);
        booking.setTravelDate(travelDate);
        booking.setBookingDate(LocalDateTime.now());

        // ✅ Set a default booking status if null
        if (booking.getBookingStatus() == null) {
            booking.setBookingStatus(BookingStatus.CONFIRMED); // Change this based on your enum
        }

        // ✅ Set a default payment status if null
        if (booking.getPaymentStatus() == null) {
            booking.setPaymentStatus(PaymentStatus.PENDING); // Change based on your enum
        }

        return bookingRepository.save(booking);
    }

    // Update booking status
    @Transactional
    public void updateBookingStatus(long bookingId, BookingStatus status) {
        bookingRepository.updateBookingStatus(bookingId, status);
    }

    // Update payment status
    @Transactional
    public void updatePaymentStatus(long bookingId, PaymentStatus status) {
        bookingRepository.updatePaymentStatus(bookingId, status);
    }

    // Delete a booking by ID
    @Transactional
    public void deleteBooking(long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}
