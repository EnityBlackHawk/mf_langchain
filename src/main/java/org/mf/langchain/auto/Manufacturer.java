package org.mf.langchain.auto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "manufacturer")
public class Manufacturer {
    @Id
    private String id;
    private String name;
}
