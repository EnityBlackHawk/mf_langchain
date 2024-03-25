package org.mf.langchain.service;


import org.mf.langchain.generics.GenericService;
import org.mf.langchain.model.Manufacturer;
import org.mf.langchain.repositories.ManufacturerRepository;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerService extends GenericService<Manufacturer, Integer, ManufacturerRepository> {

    public ManufacturerService(ManufacturerRepository repository) {
        super(repository);
    }
}
