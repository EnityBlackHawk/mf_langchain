package org.mf.langchain.auto;

// AircraftRepository.java
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AircraftRepository extends MongoRepository<Aircraft, String> {
}
