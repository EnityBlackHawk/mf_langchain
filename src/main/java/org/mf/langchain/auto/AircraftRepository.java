package org.mf.langchain.auto;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends MongoRepository<Aircraft, String> {
}
