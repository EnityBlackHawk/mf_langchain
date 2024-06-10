package org.mf.langchain.controller;

import org.mf.langchain.DTO.TestResultDTO;
import org.mf.langchain.model.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.mf.langchain.service.PersistenceService;

import java.util.List;

@RestController
@RequestMapping("/api/persistence")
public class PersistenceController {

    private final PersistenceService persistenceService;

    public PersistenceController(@Autowired PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @GetMapping()
    public List<TestResult> getAll() {
        return persistenceService.getTestResults();
    }

    @PostMapping()
    public TestResult persist(@RequestBody TestResultDTO testResultDto) {
        return persistenceService.persist(testResultDto);
    }

    @GetMapping("/{id}/response")
    public String get(@PathVariable Integer id) {
        return persistenceService.getTestResultResponse(id);
    }

    @GetMapping("/{id}/request")
    public String getRequest(@PathVariable Integer id) {
        return persistenceService.getTestResultRequest(id);
    }

    @GetMapping("/{id}")
    public TestResult getTestResult(@PathVariable Integer id) {
        return persistenceService.getTestResult(id);
    }
}
