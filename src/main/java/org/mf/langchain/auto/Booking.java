package org.mf.langchain.auto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("booking")
public class Booking {
    @Id
    private String id;
    
    @DBRef
    private Flight flight;
    
    @DBRef
    private Passenger passenger;
    
    private String seat;
}
