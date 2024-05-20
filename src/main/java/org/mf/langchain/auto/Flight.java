package org.mf.langchain.auto;

// Flight.java
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class Flight {
    @Id
    private String number;
    private Integer gate;
    private String departureTimeScheduled;
    private String departureTimeActual;
    private String arrivalTimeScheduled;
    private String arrivalTimeActual;
    @DBRef
    private Aircraft aircraft;
    @DBRef
    private Airport airportFrom;
    @DBRef
    private Airport airportTo;
    @DBRef
    private Flight connectsTo;
}
