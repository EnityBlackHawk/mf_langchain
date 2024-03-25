package org.mf.langchain.service;


import org.mf.langchain.generics.GenericService;
import org.mf.langchain.model.Airline;
import org.mf.langchain.repositories.AirlineRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirlineService extends GenericService<Airline, Integer, AirlineRepository> {

    public AirlineService(AirlineRepository repository) {
        super(repository);
    }

    public Optional<Airline> findByIataAndIcao(String iata, String icao)
    {
        return repository.findByIataAndIcao(iata, icao);
    }

}
