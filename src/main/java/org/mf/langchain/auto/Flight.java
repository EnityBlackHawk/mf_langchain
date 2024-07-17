package org.mf.langchain.auto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

@Data
@Document(collection = "flight")
public class Flight {
    @Id
    private String id;
    private String airportFrom;
    private String airportTo;
    private Date departureTimeScheduled;
    private Date departureTimeActual;
    private Date arrivalTimeScheduled;
    private Date arrivalTimeActual;
    private int gate;
    @DBRef
    private Aircraft aircraft;
    private String connectsTo;
}
