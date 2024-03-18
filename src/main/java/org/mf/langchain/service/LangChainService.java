package org.mf.langchain.service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.localai.LocalAiChatModel;
import dev.langchain4j.model.localai.LocalAiStreamingChatModel;
import org.jetbrains.annotations.Nullable;
import org.mf.langchain.util.LanguageModel;
import org.mf.langchain.StreamLanguageModel;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.Consumer;

@Service
public class LangChainService {

    private final LanguageModel lm;
    private final StreamLanguageModel slm;

    LangChainService() {
        lm = new LanguageModel(LocalAiChatModel.builder()
                .modelName("phi-2")
                .baseUrl("http://localhost:8080")
                .build());

        slm = new StreamLanguageModel(LocalAiStreamingChatModel.builder()
                .modelName("phi-2")
                .baseUrl("http://localhost:8080")
                .timeout(Duration.ofDays(1))
                .temperature(0.8)
                .build());
    }

    public String Generate(String prompt)
    {
        return lm.RunBlocking(prompt);
    }

    public void GenerateStream(String prompt, Consumer<String> onNext, Consumer<Throwable> onError, @Nullable Consumer<AiMessage> onComplete) {
        slm.generate(prompt, onNext, onError, onComplete);
    }

}
