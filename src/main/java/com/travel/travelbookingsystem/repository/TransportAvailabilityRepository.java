package com.travel.travelbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.travel.travelbookingsystem.entity.TransportAvailability;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransportAvailabilityRepository extends JpaRepository<TransportAvailability, Long> {

    List<TransportAvailability> findAll();

    List<TransportAvailability> findByTransportIdAndTransportTypeAndTravelDate(Long transportId, String transportType, LocalDate travelDate);

    List<TransportAvailability> findByTransportIdAndTransportType(Long transportId, String transportType);

    List<TransportAvailability> findByTransportType(String transportType);

    List<TransportAvailability> findByTransportId(Long transportId);

    List<TransportAvailability> findByTravelDate(LocalDate travelDate);

    boolean existsByTransportIdAndTransportTypeAndTravelDate(Long transportId, String transportType, LocalDate travelDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM TransportAvailability ta WHERE ta.transportId = :transportId AND ta.transportType = :transportType")
    void deleteByTransportIdAndTransportType(Long transportId, String transportType);

    @Modifying
    @Transactional
    @Query("UPDATE TransportAvailability ta SET ta.availableSeats = ta.availableSeats - 1 " +
           "WHERE ta.transportId = :transportId AND ta.transportType = :transportType " +
           "AND ta.travelDate = :travelDate AND ta.availableSeats > 0")
    int reduceAvailableSeats(Long transportId, String transportType, LocalDate travelDate);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM transport_availability WHERE travel_date < CURRENT_DATE", nativeQuery = true)
    void deletePastRecords();

    @Modifying
    @Transactional
    @Query(value = """
	DELETE FROM transport_availability ta
	USING (
	    SELECT ta.id
	    FROM transport_availability ta
	    LEFT JOIN train t ON ta.transport_id = t.train_id AND ta.transport_type = 'TRAIN'
	    LEFT JOIN bus b ON ta.transport_id = b.bus_id AND ta.transport_type = 'BUS'
	    LEFT JOIN flight f ON ta.transport_id = f.flight_id AND ta.transport_type = 'FLIGHT'
	    WHERE ta.travel_date = CURRENT_DATE
	      AND (
	        (ta.transport_type = 'TRAIN' AND t.departure_time < CURRENT_TIME) OR
	        (ta.transport_type = 'BUS' AND b.departure_time < CURRENT_TIME) OR
	        (ta.transport_type = 'FLIGHT' AND f.departure_time < CURRENT_TIME)
	      )
	) expired
	WHERE ta.id = expired.id;
        """, nativeQuery = true)
    void deleteDepartedTransports();

    @Modifying
    @Transactional
    @Query(value = """        
        INSERT INTO transport_availability (available_seats, transport_id, transport_type, travel_date)
        SELECT 
            total_seats AS available_seats,
            transport_id,
            transport_type,
            CURRENT_DATE + INTERVAL '6 days' AS travel_date
        FROM (
            SELECT train_id AS transport_id, total_seats, 'TRAIN' AS transport_type FROM train
            UNION ALL
            SELECT bus_id AS transport_id, total_seats, 'BUS' AS transport_type FROM bus
            UNION ALL
            SELECT flight_id AS transport_id, total_seats, 'FLIGHT' AS transport_type FROM flight
        ) AS transports
        WHERE NOT EXISTS (
            SELECT 1 FROM transport_availability ta 
            WHERE ta.transport_id = transports.transport_id 
            AND ta.transport_type = transports.transport_type 
            AND ta.travel_date = CURRENT_DATE + INTERVAL '6 days'
        )
        """, nativeQuery = true)
    void insertNextDayRecords();
}
