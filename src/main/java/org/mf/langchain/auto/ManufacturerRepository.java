package org.mf.langchain.auto;

// ManufacturerRepository.java
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ManufacturerRepository extends MongoRepository<Manufacturer, String> {
}
