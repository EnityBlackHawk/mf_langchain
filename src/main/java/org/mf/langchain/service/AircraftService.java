package org.mf.langchain.service;


import org.mf.langchain.generics.GenericService;
import org.mf.langchain.model.Aircraft;
import org.mf.langchain.repositories.AircraftRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AircraftService extends GenericService<Aircraft, String, AircraftRepository> {


    public AircraftService(AircraftRepository repository) {
        super(repository);
    }

    public Optional<Aircraft> findByRegistration(String registration)
    {
        return repository.findByRegistration(registration);
    }
}
