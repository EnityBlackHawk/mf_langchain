package org.mf.langchain.auto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Aircraft {
    @Id
    private String id;
    private String type;
    @DBRef
    private Airline airline;
    @DBRef
    private Manufacturer manufacturer;
    private String registration;
    private int maxPassengers;
}
