package org.mf.langchain.repositories;

import org.mf.langchain.model.Workload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkloadRepository extends JpaRepository<Workload, Integer> {
}
