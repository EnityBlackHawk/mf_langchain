package org.mf.langchain.auto;

// Manufacturer.java
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Manufacturer {
    @Id
    private String id;
    private String name;
}
