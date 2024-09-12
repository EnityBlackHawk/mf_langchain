package org.mf.langchain.auto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    private String number;
    private Airport airportFrom;
    private Airport airportTo;
    private LocalDateTime departureTimeScheduled;
    private LocalDateTime departureTimeActual;
    private LocalDateTime arrivalTimeScheduled;
    private LocalDateTime arrivalTimeActual;
    private Integer gate;
    private Aircraft aircraft;
    private String connectsTo; // using flight number
}
