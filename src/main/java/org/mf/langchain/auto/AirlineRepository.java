package org.mf.langchain.auto;

// AirlineRepository.java
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AirlineRepository extends MongoRepository<Airline, String> {
}
