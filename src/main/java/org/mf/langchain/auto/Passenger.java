package org.mf.langchain.auto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "passenger")
public class Passenger {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String passportNumber;
}
