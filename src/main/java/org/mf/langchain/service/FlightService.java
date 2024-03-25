package org.mf.langchain.service;


import org.mf.langchain.generics.GenericService;
import org.mf.langchain.model.Flight;
import org.mf.langchain.repositories.FlightRepository;
import org.springframework.stereotype.Service;

@Service
public class FlightService extends GenericService<Flight, String, FlightRepository> {
    public FlightService(FlightRepository repository) {
        super(repository);
    }
}
