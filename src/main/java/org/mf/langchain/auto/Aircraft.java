package org.mf.langchain.auto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Document
public class Aircraft {
    private Integer airline;
    private Integer id;
    @DBRef
    private Manufacturer manufacturer;
    private Integer maxPassengers;
    private String registration;
    private String type;

}

@Document
public class Airline {
    private Integer id;
    private String iata;
    private String icao;
    private String name;

}

@Document
public class Airport {
    private String city;
    private String country;
    private String id;
    private String name;

}

@Document
public class Flight {
    @DBRef
    private Aircraft aircraft;
    private Integer gate;
    private Long arrivalTimeActual;
    private Long arrivalTimeScheduled;
    private Long departureTimeActual;
    private Long departureTimeScheduled;
    @DBRef
    private Airport airportFrom;
    @DBRef
    private Airport airportTo;
    @DBRef
    private Flight connectsTo;
    private String number;

}

@Document
public class Manufacturer {
    private Integer id;
    private String name;

}
