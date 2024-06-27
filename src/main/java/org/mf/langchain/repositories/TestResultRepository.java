package org.mf.langchain.repositories;

import org.mf.langchain.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Integer> {
    Optional<TestResult> findTopByOrderByIdDesc();
}
