package org.mf.langchain.DTO;

import org.mf.langchain.enums.ProcessStepName;

public record ModelDTO(
    String model,
    int tokens_used
) {}
