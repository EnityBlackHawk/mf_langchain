package org.mf.langchain.auto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
