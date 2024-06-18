package org.mf.langchain.repositories;

import org.mf.langchain.model.RelationsCardinality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationsCardinalityRepository extends JpaRepository<RelationsCardinality, Integer>{
}
