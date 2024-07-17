package org.mf.langchain.auto;

// Aircraft.java
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Aircraft {
    @Id
    private String id;
    private String type;
    private Airline airline;
    private Manufacturer manufacturer;
    private String registration;
    private int maxPassengers;
}
