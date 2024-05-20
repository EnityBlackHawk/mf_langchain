package org.mf.langchain.auto;

// Airline.java
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Airline {
    @Id
    private Integer id;
    private String iata;
    private String icao;
    private String name;
}
