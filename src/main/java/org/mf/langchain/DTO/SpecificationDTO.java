package org.mf.langchain.DTO;

import java.util.List;

public record SpecificationDTO(
        String name,
        String data_source,
        List<WorkloadDTO> workload,
        Boolean allow_ref,
        Boolean prioritize_performance,
        String framework,
        List<String> custom_prompt,
        String LLM,
        Credentials credentials,
        List<RelationsCardinalityDTO> cardinality
) {
    public record WorkloadDTO(Integer regularity, String query) {}

    public static SpecificationDTO overrideDataSource(SpecificationDTO spec, String dataSource) {
        return new SpecificationDTO(
                spec.name(),
                dataSource,
                spec.workload(),
                spec.allow_ref(),
                spec.prioritize_performance(),
                spec.framework(),
                spec.custom_prompt(),
                spec.LLM(),
                spec.credentials(),
                spec.cardinality()
        );
    }

    public static SpecificationDTO overrideCardinality(SpecificationDTO spec, List<RelationsCardinalityDTO> cardinality) {
        return new SpecificationDTO(
                spec.name(),
                spec.data_source(),
                spec.workload(),
                spec.allow_ref(),
                spec.prioritize_performance(),
                spec.framework(),
                spec.custom_prompt(),
                spec.LLM(),
                spec.credentials(),
                cardinality
        );
    }
}
