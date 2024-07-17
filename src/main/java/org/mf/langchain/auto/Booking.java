package org.mf.langchain.auto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Document(collection = "booking")
public class Booking {
    @Id
    private String id;
    private String flight;
    @DBRef
    private Passenger passenger;
    private String seat;
}
