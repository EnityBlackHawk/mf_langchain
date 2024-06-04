package org.mf.langchain.service;

import org.mf.langchain.DTO.SpecificationDTO;
import org.mf.langchain.DTO.TestResultDTO;
import org.mf.langchain.exception.IdNotFoundException;
import org.mf.langchain.model.Specification;
import org.mf.langchain.model.TestResult;
import org.mf.langchain.model.Workload;
import org.mf.langchain.repositories.SpecificationRepository;
import org.mf.langchain.repositories.TestResultRepository;
import org.mf.langchain.repositories.WorkloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class PersistenceService {

    private final SpecificationRepository specificationRepository;
    private final TestResultRepository testResultRepository;
    private final WorkloadRepository workloadRepository;


    public PersistenceService(@Autowired SpecificationRepository specificationRepository,
                              @Autowired TestResultRepository testResultRepository,
                              @Autowired WorkloadRepository workloadRepository) {
        this.specificationRepository = specificationRepository;
        this.testResultRepository = testResultRepository;
        this.workloadRepository = workloadRepository;
    }

    public TestResult persist(TestResultDTO testResultdto) {
        var testResult = new TestResult(
                null,
                testResultdto.request(),
                this.persist(testResultdto.spec()),
                testResultdto.response(),
                testResultdto.tokenCount(),
                new Date(System.currentTimeMillis())
        );
        return testResultRepository.save(testResult);
    }

    public Specification persist(SpecificationDTO specificationDto) {
        var specification = new Specification(
                null,
                specificationDto.name(),
                specificationDto.data_source(),
                specificationDto.workload().stream().map(this::persist).toList(),
                specificationDto.allow_ref(),
                specificationDto.prioritize_performance(),
                specificationDto.framework(),
                specificationDto.custom_prompt(),
                specificationDto.LLM()
        );
        return specificationRepository.save(specification);
    }

    public Workload persist(SpecificationDTO.WorkloadDTO workloadDto) {
        var workload = new Workload(
                null,
                workloadDto.regularity(),
                workloadDto.query()
        );
        return workloadRepository.save(workload);
    }

    public List<TestResult> getTestResults() {
        return testResultRepository.findAll();
    }

    public String getTestResultResponse(Integer id) {
        return testResultRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id)).getResponse();
    }

    public String getTestResultRequest(Integer id) {
        return testResultRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id)).getRequest();
    }

}
