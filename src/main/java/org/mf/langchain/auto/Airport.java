package org.mf.langchain.auto;

// Airport.java
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Airport {
    @Id
    private String id;
    private String city;
    private String country;
    private String name;
}
