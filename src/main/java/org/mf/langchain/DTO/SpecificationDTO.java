package org.mf.langchain.DTO;

import java.util.List;

public record SpecificationDTO(
        String name,
        String data_source,
        List<Workload> workload,
        Boolean allow_ref,
        Boolean prioritize_performance,
        String framework,
        String custom_prompt,
        String LLM
) {
    public record Workload(Integer regularity, String query) {}
}
