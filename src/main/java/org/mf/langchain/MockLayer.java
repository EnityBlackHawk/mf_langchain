package org.mf.langchain;

import org.mf.langchain.enums.ProcessStepName;

import java.util.*;

public class MockLayer {

    public static Map<ProcessStepName, String> values = Map.of();

    public static boolean isActivated = false;

    public static String MOCK_LLM_RESPOSE = """
            To convert the relational database schema into a MongoDB structure while following the criteria provided, we will embed certain documents based on their relationships and optimize the structure for efficient querying. Below are the recommended classes along with repository interfaces for the Spring Data MongoDB framework.
            
            ### Aircraft.java
            ```java
            import org.springframework.data.annotation.Id;
            import org.springframework.data.mongodb.core.mapping.Document;
            
            @Document(collection = "aircraft")
            public class Aircraft {
                @Id
                private String id;
                private String type;
                private Airline airline;
                private Manufacturer manufacturer;
                private String registration;
                private int maxPassengers;
            
                // Getters and Setters
            }
            ```
            
            ### Airline.java
            ```java
            import org.springframework.data.annotation.Id;
            import org.springframework.data.mongodb.core.mapping.Document;
            
            @Document(collection = "airline")
            public class Airline {
                @Id
                private String id;
                private String name;
            
                // Getters and Setters
            }
            ```
            
            ### Airport.java
            ```java
            import org.springframework.data.annotation.Id;
            import org.springframework.data.mongodb.core.mapping.Document;
            
            @Document(collection = "airport")
            public class Airport {
                @Id
                private String id;
                private String name;
                private String city;
                private String country;
            
                // Getters and Setters
            }
            ```
            
            ### Booking.java
            ```java
            import org.springframework.data.annotation.Id;
            import org.springframework.data.mongodb.core.mapping.Document;
            
            @Document(collection = "booking")
            public class Booking {
                @Id
                private String id;
                private String flightNumber;
                private Passenger passenger;
                private String seat;
            
                // Getters and Setters
            }
            ```
            
            ### Flight.java
            ```java
            import org.springframework.data.annotation.Id;
            import org.springframework.data.mongodb.core.mapping.Document;
            
            @Document(collection = "flight")
            public class Flight {
                @Id
                private String number;
                private Airport airportFrom;
                private Airport airportTo;
                private String departureTimeScheduled;
                private String departureTimeActual;
                private String arrivalTimeScheduled;
                private String arrivalTimeActual;
                private int gate;
                private Aircraft aircraft;
                private String connectsTo;
            
                // Getters and Setters
            }
            ```
            
            ### Manufacturer.java
            ```java
            import org.springframework.data.annotation.Id;
            import org.springframework.data.mongodb.core.mapping.Document;
            
            @Document(collection = "manufacturer")
            public class Manufacturer {
                @Id
                private String id;
                private String name;
            
                // Getters and Setters
            }
            ```
            
            ### Passenger.java
            ```java
            import org.springframework.data.annotation.Id;
            import org.springframework.data.mongodb.core.mapping.Document;
            
            @Document(collection = "passenger")
            public class Passenger {
                @Id
                private String id;
                private String firstName;
                private String lastName;
                private String passportNumber;
            
                // Getters and Setters
            }
            ```
            """;

    public static final String MOCK_GENERATE_MODEL = """
            // Aircraft collection
            {
            \t"id": NumberInt,
            \t"type": String,
            \t"airline": {
            \t\t"id": NumberInt,
            \t\t"name": String
            \t},
            \t"manufacturer": {
            \t\t"id": NumberInt,
            \t\t"name": String
            \t},
            \t"registration": String,
            \t"max_passengers": NumberInt
            }

            // Airport collection
            {
            \t"id": String,
            \t"name": String,
            \t"city": String,
            \t"country": String
            }

            // Passenger collection
            {
            \t"id": NumberInt,
            \t"first_name": String,
            \t"last_name": String,
            \t"passport_number": String,
            \t"bookings": [
            \t\t{
            \t\t\t"flight_number": String,  // flight.number
            \t\t\t"seat": String
            \t\t}
            \t]
            }

            // Flight collection
            {
            \t"number": String,
            \t"airport_from": {
            \t\t"id": String,
            \t\t"name": String,
            \t\t"city": String,
            \t\t"country": String
            \t},
            \t"airport_to": {
            \t\t"id": String,
            \t\t"name": String,
            \t\t"city": String,
            \t\t"country": String
            \t},
            \t"departure_time_scheduled": Date,
            \t"departure_time_actual": Date,
            \t"arrival_time_scheduled": Date,
            \t"arrival_time_actual": Date,
            \t"gate": NumberInt,
            \t"aircraft": {
            \t\t"id": NumberInt,
            \t\t"type": String,
            \t\t"airline": {
            \t\t\t"id": NumberInt,
            \t\t\t"name": String
            \t\t}
            \t},
            \t"connects_to": String  // flight.number of connecting flight
            }""";

    public static final Map<String, String> MOCK_GENERATE_JAVA_CODE = Map.of( "Airline", """
            import org.springframework.data.annotation.Id;
            public class Airline {
                @Id
                private String id;
                private String name;
            }
            """,
            "Manufacturer",
            """
             import org.springframework.data.annotation.Id;
             public class Manufacturer {
                @Id
                private String id;
                private String name;
            }
            """,
            "Aircraft",
            """
            import org.springframework.data.mongodb.core.mapping.DBRef;
            import lombok.Data;
            @Data
            public class Aircraft {
                private String id;
                private String type;
                @DBRef
                private Airline airline;
                private Manufacturer manufacturer;
                private String registration;
                private int maxPassengers;
             }
            """);


//    public void init(ProcessStepName[] ... steps) {
//        isActivated = true;
//        var l = Arrays.stream(steps).toList();
//        if(l.contains(ProcessStepName.GENERATE_MODEL)) {
//            values.put(ProcessStepName.GENERATE_MODEL, MOCK_GENERATE_MODEL);
//        }
//        if(l.contains(ProcessStepName.GENERATE_JAVA_CODE)) {
//            values.put(ProcessStepName.GENERATE_JAVA_CODE, MOCK_GENERATE_JAVA_CODE);
//        }
//
//    }

    public static Optional<String> getValue(ProcessStepName step) {
        return Optional.ofNullable(values.get(step));
    }

}
