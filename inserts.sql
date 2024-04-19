-- INSERT statements for tb_manufacturer table
INSERT INTO tb_manufacturer (id, name) VALUES
(1, 'Boeing'),
(2, 'Airbus'),
(3, 'Bombardier'),
(4, 'Embraer'),
(5, 'Lockheed Martin'),
(6, 'Northrop Grumman'),
(7, 'Cessna'),
(8, 'Dassault Aviation'),
(9, 'Gulfstream Aerospace'),
(10, 'Mitsubishi Aircraft');

-- INSERT statements for 100 aircrafts
INSERT INTO tb_aircraft (id, type, airline, manufacturer_id, registration, max_passengers) VALUES
(1, 'TypeA', 1, 1, 'REG123', 200),
(2, 'TypeB', 2, 2, 'REG124', 150),
(3, 'TypeC', 3, 3, 'REG125', 220),
(4, 'TypeD', 4, 4, 'REG126', 175),
(5, 'TypeE', 5, 5, 'REG127', 210),
(6, 'TypeZ', 5, 4, 'REG199', 150);


-- INSERT statements for 100 flights
INSERT INTO tb_flight (number, airport_from, airport_to, departure_time_scheduled, departure_time_actual, arrival_time_scheduled, arrival_time_actual, gate, aircraft_id, connects_to) VALUES
('FL100', 'JFK', 'LAX', '2024-04-21 10:00:00', '2024-04-21 10:05:00', '2024-04-21 14:00:00', '2024-04-21 14:10:00', 5, 1, NULL),
('FL101', 'LAX', 'ORD', '2024-04-22 11:00:00', '2024-04-22 11:10:00', '2024-04-22 14:00:00', '2024-04-22 14:15:00', 3, 2, NULL),
('FL102', 'ORD', 'DFW', '2024-04-23 12:00:00', '2024-04-23 12:05:00', '2024-04-23 15:00:00', '2024-04-23 15:10:00', 4, 3, NULL),
('FL103', 'DFW', 'DEN', '2024-04-24 10:30:00', '2024-04-24 10:35:00', '2024-04-24 13:30:00', '2024-04-24 13:35:00', 6, 4, NULL),
('FL104', 'DEN', 'ATL', '2024-04-25 09:00:00', '2024-04-25 09:10:00', '2024-04-25 12:00:00', '2024-04-25 12:10:00', 2, 5, NULL),
('FL105', 'ATL', 'MIA', '2024-04-26 08:00:00', '2024-04-26 08:10:00', '2024-04-26 11:00:00', '2024-04-26 11:10:00', 1, 6, NULL),
('FL106', 'MIA', 'JFK', '2024-04-27 07:00:00', '2024-04-27 07:10:00', '2024-04-27 10:00:00', '2024-04-27 10:10:00', 7, 1, NULL),
('FL107', 'JFK', 'LAX', '2024-04-28 10:00:00', '2024-04-28 10:10:00', '2024-04-28 14:00:00', '2024-04-28 14:10:00', 5, 2, NULL),
('FL108', 'LAX', 'ORD', '2024-04-29 11:00:00', '2024-04-29 11:10:00', '2024-04-29 14:00:00', '2024-04-29 14:10:00', 3, 3, NULL),
('FL109', 'ORD', 'DFW', '2024-04-30 12:00:00', '2024-04-30 12:10:00', '2024-04-30 15:00:00', '2024-04-30 15:10:00', 4, 4, NULL),
('FL110', 'DFW', 'DEN', '2024-05-01 10:30:00', '2024-05-01 10:40:00', '2024-05-01 13:30:00', '2024-05-01 13:40:00', 6, 5, NULL),
('FL199', 'MIA', 'SFO', '2024-04-30 08:00:00', '2024-04-30 08:10:00', '2024-04-30 11:00:00', '2024-04-30 11:15:00', 7, 5, NULL);

