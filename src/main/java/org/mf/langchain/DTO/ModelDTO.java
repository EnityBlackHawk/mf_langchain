package org.mf.langchain.DTO;

import org.mf.langchain.enums.ProcessStepName;

public class ModelDTO {
    private String model;
    private int tokens_used;

    public ModelDTO() {
    }

    public ModelDTO(String model, int tokens_used) {
        this.model = model;
        this.tokens_used = tokens_used;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTokens_used() {
        return tokens_used;
    }

    public void setTokens_used(int tokens_used) {
        this.tokens_used = tokens_used;
    }
}
