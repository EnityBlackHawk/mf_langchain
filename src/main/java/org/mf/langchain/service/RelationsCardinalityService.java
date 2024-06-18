package org.mf.langchain.service;

import org.mf.langchain.model.RelationsCardinality;
import org.mf.langchain.repositories.RelationsCardinalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationsCardinalityService extends CRUDService<RelationsCardinality, Integer, RelationsCardinalityRepository> {
    public RelationsCardinalityService(@Autowired RelationsCardinalityRepository repository) {
        super(repository);
    }
}
