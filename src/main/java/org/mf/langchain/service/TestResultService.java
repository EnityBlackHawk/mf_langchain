package org.mf.langchain.service;

import org.mf.langchain.model.TestResult;
import org.mf.langchain.repositories.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestResultService extends CRUDService<TestResult, Integer, TestResultRepository>{


    public TestResultService(@Autowired TestResultRepository repository) {
        super(repository);
    }
}
