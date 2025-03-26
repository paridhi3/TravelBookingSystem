package com.travel.travelbookingsystem.repository;

import com.travel.travelbookingsystem.entity.Booking;
import com.travel.travelbookingsystem.entity.BookingStatus;
import com.travel.travelbookingsystem.entity.PaymentStatus;
import com.travel.travelbookingsystem.entity.TransportType;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Custom query methods:

    // Find bookings by passenger ID
	List<Booking> findByPassenger_PassengerId(Long passengerId);

    // Find bookings by transport type
    List<Booking> findByTransportType(String transportType);
    
    // Find bookings by transport type, transport id, and travel date
    List<Booking> findByTransportTypeAndTransportIdAndTravelDate(TransportType transportType, long transportId, LocalDate travelDate);

    // Update booking status
    @Transactional
    @Modifying
    @Query("UPDATE Booking b SET b.bookingStatus = :status WHERE b.bookingId = :bookingId")
    void updateBookingStatus(@Param("bookingId") Long bookingId, @Param("status") BookingStatus status);

    // Update payment status
    @Transactional
    @Modifying
    @Query("UPDATE Booking b SET b.paymentStatus = :status WHERE b.bookingId = :bookingId")
    void updatePaymentStatus(@Param("bookingId") Long bookingId, @Param("status") PaymentStatus status);
}
