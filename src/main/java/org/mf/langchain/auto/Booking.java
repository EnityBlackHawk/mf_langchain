package org.mf.langchain.auto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "booking")
public class Booking {
    @Id
    private String id;
    private Flight flight;
    private Passenger passenger;
    private String seat;
}

@Data
class FlightReference {
    private String number;
}
