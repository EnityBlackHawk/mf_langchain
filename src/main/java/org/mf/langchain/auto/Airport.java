package org.mf.langchain.auto;

// Airport.java
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Airport {
    @Id
    private String id;
    private String name;
    private String city;
    private String country;
}
