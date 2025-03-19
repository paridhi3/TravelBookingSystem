package com.travel.travelbookingsystem.controller;

import com.travel.travelbookingsystem.entity.Booking;
import com.travel.travelbookingsystem.entity.TransportType;
import com.travel.travelbookingsystem.service.BookingService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

/**
 * BookingController Methods:
 * 1. getAllBookings(): Retrieve all bookings.
 * 2. getBookingById(long bookingId): Retrieve a booking by booking ID.
 * 3. getBookingsByPassengerId(long passengerId): Retrieve bookings by passenger ID.
 * 4. getBookingsByTransportType(String transportType): Retrieve bookings by transport type.
 * 5. createBooking(Booking booking, Long passengerId, Long transportId, LocalDate travelDate): Create a booking.
 * 6. updateBookingStatus(long bookingId, String status): Update the status of a booking.
 * 7. updatePaymentStatus(long bookingId, String status): Update the payment status of a booking.
 * 8. deleteBooking(long bookingId): Delete a booking.
 */

@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Retrieve all bookings
    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving bookings: " + e.getMessage());
        }
    }

    // Retrieve a booking by ID
    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable long bookingId) {
        try {
            Booking booking = bookingService.getBookingById(bookingId);
            return booking != null ? ResponseEntity.ok(booking)
                                   : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error fetching booking: " + e.getMessage());
        }
    }

    // Retrieve bookings by passenger ID
    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<?> getBookingsByPassengerId(@PathVariable long passengerId) {
        try {
            List<Booking> bookings = bookingService.getBookingsByPassengerId(passengerId);
            return bookings.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                                      .body("No bookings found for this passenger")
                                      : ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving bookings: " + e.getMessage());
        }
    }

    // Retrieve bookings by transport type
    @GetMapping("/transport/{transportType}")
    public ResponseEntity<?> getBookingsByTransportType(@PathVariable String transportType) {
        try {
            List<Booking> bookings = bookingService.getBookingsByTransportType(transportType);
            return bookings.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                                      .body("No bookings found for this transport type")
                                      : ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving bookings: " + e.getMessage());
        }
    }
    
    // Retrieve bookings by transport type, transport id, and travel date
    @GetMapping("/transport/{transportType}/{transportId}")
    public ResponseEntity<?> getBookingsByTransportTypeTransportIdTravelDate(
        @PathVariable String transportType,
        @PathVariable long transportId,
        @RequestParam String travelDate // Accept travelDate as a query parameter
    ) {
        try {
            // Convert String to Enum
            TransportType typeEnum;
            try {
                typeEnum = TransportType.valueOf(transportType.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body("Invalid transport type: " + transportType);
            }

            // Convert String to LocalDate
            LocalDate date;
            try {
                date = LocalDate.parse(travelDate);
            } catch (DateTimeParseException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body("Invalid travel date format. Expected format: YYYY-MM-DD");
            }

            List<Booking> bookings = bookingService.getBookingsByTransportTypeTransportId(typeEnum, transportId, date);
            return bookings.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                                      .body("No bookings found for this transport type, transport id, and travel date")
                                      : ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving bookings: " + e.getMessage());
        }
    }

    // Retrieve booked seats by transport type, transport id, and travel date
    @GetMapping("/bookedSeats")
    public ResponseEntity<List<String>> getBookedSeats(@RequestParam String transportType,
                                                       @RequestParam long transportId,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate travelDate) {
        try {
            TransportType typeEnum;
            try {
                typeEnum = TransportType.valueOf(transportType.toUpperCase()); // Convert string to Enum
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(Collections.singletonList("Invalid transport type: " + transportType));
            }

            List<String> bookedSeats = bookingService.getBookedSeats(typeEnum, transportId, travelDate);

            return ResponseEntity.ok(bookedSeats); // ✅ Always return 200 with an empty list if no seats are booked
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Collections.singletonList("Error fetching booked seats: " + e.getMessage()));
        }
    }


    
    @PostMapping("/book/{passengerId}/{transportId}")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking,
                                           @PathVariable Long passengerId, 
                                           @PathVariable Long transportId) {
        try {
            Booking savedBooking = bookingService.createBooking(booking, passengerId, transportId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid booking request: " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Booking failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error booking: " + e.getMessage());
        }
    }

    // Update booking status
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<?> updateBookingStatus(@PathVariable long bookingId, @RequestParam String status) {
        try {
            bookingService.updateBookingStatus(bookingId, status);
            return ResponseEntity.ok("Booking status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error updating booking status: " + e.getMessage());
        }
    }

    // Update payment status
    @PutMapping("/{bookingId}/payment")
    public ResponseEntity<?> updatePaymentStatus(@PathVariable long bookingId, @RequestParam String status) {
        try {
            bookingService.updatePaymentStatus(bookingId, status);
            return ResponseEntity.ok("Payment status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error updating payment status: " + e.getMessage());
        }
    }

    // Delete a booking
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteBooking(@PathVariable long bookingId) {
        try {
            bookingService.deleteBooking(bookingId);
            return ResponseEntity.ok("Booking deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error deleting booking: " + e.getMessage());
        }
    }
}
