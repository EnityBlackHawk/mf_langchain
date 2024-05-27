package org.mf.langchain.service;

import org.mf.langchain.model.Specification;
import org.mf.langchain.repositories.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecificationService extends CRUDService<Specification, Integer, SpecificationRepository>{
    public SpecificationService(@Autowired SpecificationRepository repository) {
        super(repository);
    }
}
