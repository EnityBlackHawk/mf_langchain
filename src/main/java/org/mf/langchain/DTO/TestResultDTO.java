package org.mf.langchain.DTO;

import jakarta.persistence.OneToOne;
import org.mf.langchain.model.Specification;

public record TestResultDTO(
        String request,
        SpecificationDTO spec,
        String response,
        Integer tokenCount
) {}
