package org.mf.langchain.auto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    private String id;
    private String firstName;
    private String lastName;
    private String passportNumber;
}
