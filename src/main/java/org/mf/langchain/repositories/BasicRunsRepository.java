package org.mf.langchain.repositories;

import org.mf.langchain.model.BasicRuns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicRunsRepository extends JpaRepository<BasicRuns, Integer> {
}
