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
        String LLM
) {
    public record WorkloadDTO(Integer regularity, String query) {}
}
