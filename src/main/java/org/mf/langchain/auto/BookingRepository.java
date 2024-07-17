package org.mf.langchain.auto;

// BookingRepository.java
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {
}
