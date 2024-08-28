package org.mf.langchain.auto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("flight")
public class Flight {
    @Id
    private String number;
    
    @DBRef
    private Airport airportFrom;
    
    @DBRef
    private Airport airportTo;
    
    private LocalDateTime departureTimeScheduled;
    private LocalDateTime departureTimeActual;
    private LocalDateTime arrivalTimeScheduled;
    private LocalDateTime arrivalTimeActual;
    private int gate;
    
    @DBRef
    private Aircraft aircraft;
    
    @DBRef
    private Flight connectsTo;
}
