package org.mf.langchain.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.localai.LocalAiChatModel;
import org.mf.langchain.Assistant;
import org.mf.langchain.LanguageModel;
import org.springframework.stereotype.Service;

@Service
public class LangChainService {

    private final LanguageModel lm;
    LangChainService() {
        lm = new LanguageModel(LocalAiChatModel.builder()
                .modelName("phi-2")
                .baseUrl("http://localhost:8080")
                .build());
    }

    public String Generate(String prompt)
    {
        return lm.RunBlocking(prompt);
    }

}
