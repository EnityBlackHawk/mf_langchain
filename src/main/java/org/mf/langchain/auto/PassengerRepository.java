package org.mf.langchain.auto;

// PassengerRepository.java
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PassengerRepository extends MongoRepository<Passenger, String> {
}
