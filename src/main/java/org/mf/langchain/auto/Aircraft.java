package org.mf.langchain.auto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Document(collection = "aircraft")
public class Aircraft {
    @Id
    private String id;
    private String type;
    @DBRef
    private Airline airline;
    private Manufacturer manufacturer;
    private String registration;
    private int maxPassengers;
}
