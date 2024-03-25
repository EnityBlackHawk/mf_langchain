package org.mf.langchain.service;


import org.mf.langchain.generics.GenericService;
import org.mf.langchain.model.Airport;
import org.mf.langchain.repositories.AirportRepository;
import org.springframework.stereotype.Service;

@Service
public class AirportService extends GenericService<Airport, String, AirportRepository> {

    public AirportService(AirportRepository repository) {
        super(repository);
    }
}
