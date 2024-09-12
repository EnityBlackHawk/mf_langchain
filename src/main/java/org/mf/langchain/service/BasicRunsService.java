package org.mf.langchain.service;

import org.mf.langchain.model.BasicRuns;
import org.mf.langchain.repositories.BasicRunsRepository;
import org.springframework.stereotype.Service;

@Service
public class BasicRunsService extends CRUDService<BasicRuns, Integer, BasicRunsRepository> {
    public BasicRunsService(BasicRunsRepository repository) {
        super(repository);
    }
}
