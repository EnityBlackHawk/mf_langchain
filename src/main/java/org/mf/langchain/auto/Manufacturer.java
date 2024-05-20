package org.mf.langchain.auto;

// Manufacturer.java
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Manufacturer {
    @Id
    private Integer id;
    private String name;
}
