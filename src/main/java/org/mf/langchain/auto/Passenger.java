package org.mf.langchain.auto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "passenger")
public class Passenger {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String passportNumber;
}
