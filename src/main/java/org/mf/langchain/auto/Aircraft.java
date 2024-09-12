package org.mf.langchain.auto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aircraft {
    private String id;
    private String type;
    private Airline airline;
    private Manufacturer manufacturer;
    private String registration;
    private int maxPassengers;
}
