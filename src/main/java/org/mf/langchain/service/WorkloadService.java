package org.mf.langchain.service;

import org.mf.langchain.model.Workload;
import org.mf.langchain.repositories.WorkloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkloadService extends CRUDService<Workload, Integer, WorkloadRepository>{

    public WorkloadService(@Autowired WorkloadRepository repository) {
        super(repository);
    }
}
