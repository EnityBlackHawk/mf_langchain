package org.mf.langchain.repositories;

import org.mf.langchain.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer> {
    Optional<Airline> findByIataAndIcao(String iata, String icao);
}
