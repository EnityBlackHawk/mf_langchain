import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aircraft {
    @Id
    private Integer id;
    
    @DBRef
    private Airline airline;

    @DBRef
    private Manufacturer manufacturer;

    private Integer maxPassengers;
    private String registration;
    private String type;
}

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airline {
    @Id
    private Integer id;
    private String iata;
    private String icao;
    private String name;
}

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    @Id
    private String id;
    private String city;
    private String country;
    private String name;
}

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    private String number;
    
    @DBRef
    private Aircraft aircraft;

    private Integer gate;
    private String arrivalTimeScheduled;
    private String arrivalTimeActual;
    private String departureTimeScheduled;
    private String departureTimeActual;

    @DBRef
    private Airport airportFrom;

    @DBRef
    private Airport airportTo;

    @DBRef
    private Flight connectsTo;
}

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {
    @Id
    private Integer id;
    private String name;
}
