package org.mf.langchain.auto;

// FlightRepository.java
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightRepository extends MongoRepository<Flight, String> {
}
