package com.travel.travelbookingsystem.service;

import com.travel.travelbookingsystem.entity.Booking;
import com.travel.travelbookingsystem.entity.Passenger;
import com.travel.travelbookingsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
//import java.util.Optional;

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

@Service
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final FlightService flightService;
    private final TrainService trainService;
    private final BusService busService;
    private final PassengerService passengerService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, 
                          FlightService flightService, 
                          TrainService trainService, 
                          BusService busService,
                          PassengerService passengerService) {
        this.bookingRepository = bookingRepository;
        this.flightService = flightService;
        this.trainService = trainService;
        this.busService = busService;
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
    
    // Create a booking and update available seats
    @Transactional
    public Booking createBooking(Booking booking, Long passengerId, Long transportId) {
        // Retrieve passenger using PassengerService instead of accessing the repository directly
        Passenger passenger = passengerService.getPassengerById(passengerId);
        booking.setPassenger(passenger);

        booking.setTransportId(transportId);
        booking.setBookingDate(LocalDateTime.now());

        boolean seatsUpdated;
        String transportType = booking.getTransportType().name(); // Fixing getTransportType error

        switch (transportType) {
            case "FLIGHT":
                seatsUpdated = flightService.reduceAvailableSeats(transportId);
                break;
            case "TRAIN":
                seatsUpdated = trainService.reduceAvailableSeats(transportId);
                break;
            case "BUS":
                seatsUpdated = busService.reduceAvailableSeats(transportId);
                break;
            default:
                throw new IllegalArgumentException("Invalid transport type: " + transportType);
        }

        if (!seatsUpdated) {
            throw new IllegalStateException("No available seats for this booking");
        }

        return bookingRepository.save(booking);
    }



    // Update booking status
    @Transactional
    public void updateBookingStatus(long bookingId, String status) {
        bookingRepository.updateBookingStatus(bookingId, status);
    }

    // Update payment status
    @Transactional
    public void updatePaymentStatus(long bookingId, String status) {
        bookingRepository.updatePaymentStatus(bookingId, status);
    }

    // Delete a booking by ID
    @Transactional
    public void deleteBooking(long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}
