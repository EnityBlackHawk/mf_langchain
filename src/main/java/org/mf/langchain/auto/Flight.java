package org.mf.langchain.auto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "flight")
public class Flight {
    @Id
    private String number;
    private Airport from;
    private Airport to;
    private LocalDateTime departureTimeScheduled;
    private LocalDateTime departureTimeActual;
    private LocalDateTime arrivalTimeScheduled;
    private LocalDateTime arrivalTimeActual;
    private int gate;
    private Aircraft aircraft;
    private Flight connectsTo;
}
