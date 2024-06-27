package org.mf.langchain.DTO;

import org.mf.langchain.enums.ProcessStepName;

public record MfEntity<T>(
        ProcessStepName nextStep,
        T data
) {}
