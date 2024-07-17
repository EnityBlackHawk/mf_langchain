package org.mf.langchain.auto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Document(collection = "flight")
public class Flight {
    @Id
    private String number;
    private String airportFrom;
    private String airportTo;
    private Timestamp departureTimeScheduled;
    private Timestamp departureTimeActual;
    private Timestamp arrivalTimeScheduled;
    private Timestamp arrivalTimeActual;
    private int gate;
    @DBRef
    private Aircraft aircraft;
    private String connectsTo;
}
