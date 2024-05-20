package org.mf.langchain.auto;

// Aircraft.java
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
public class Aircraft {
    @Id
    private Integer id;
    @DBRef
    private Airline airline;
    private Integer manufacturerId;
    private Integer maxPassengers;
    private String registration;
    private String type;
}
