CREATE TABLE tb_manufacturer (
                              id INT PRIMARY KEY,
                              name TEXT
);

CREATE TABLE tb_airline(
                        id INT PRIMARY KEY,
                        name TEXT
);

CREATE TABLE tb_aircraft (
                          id INT PRIMARY KEY,
                          type TEXT UNIQUE,
                          airline INT REFERENCES tb_airline(id),
                          manufacturer_id INT REFERENCES tb_manufacturer(id),
                          registration TEXT UNIQUE,
                          max_passengers INT
);

CREATE TABLE tb_airport (
                         id VARCHAR(3) PRIMARY KEY,
                         name TEXT,
                         city TEXT,
                         country TEXT
);

CREATE TABLE tb_flight (
                        number TEXT PRIMARY KEY,
                        airport_from varchar(3) REFERENCES tb_airport(id),
                        airport_to varchar(3) REFERENCES tb_airport(id),
                        departure_time_scheduled TIMESTAMP,
                        departure_time_actual TIMESTAMP,
                        arrival_time_scheduled TIMESTAMP,
                        arrival_time_actual TIMESTAMP,
                        gate INT,
                        aircraft INT REFERENCES tb_flight(id),
                        connects_to TEXT REFERENCES tb_flight(number)
);