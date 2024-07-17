package org.mf.langchain.auto;

// AirportRepository.java
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AirportRepository extends MongoRepository<Airport, String> {
}
