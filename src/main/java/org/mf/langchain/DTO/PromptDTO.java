package org.mf.langchain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class PromptDTO {

    private String prompt;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
