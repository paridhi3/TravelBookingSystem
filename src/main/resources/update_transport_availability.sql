-- Remove past transport availability records
DELETE FROM transport_availability WHERE travel_date < CURDATE();

-- Remove today's transports that have already departed
DELETE ta FROM transport_availability ta
JOIN transport t ON ta.transport_id = t.id AND ta.transport_type = t.transport_type
WHERE ta.travel_date = CURDATE()
AND t.departureTime < CURTIME();

-- Insert new transport availability for tomorrow
INSERT INTO transport_availability (available_seats, transport_id, transport_type, travel_date)
SELECT available_seats, transport_id, transport_type, DATE_ADD(CURDATE(), INTERVAL 1 DAY)
FROM transport_availability
WHERE travel_date = CURDATE();
