package org.mf.langchain.util;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.mf.langchain.util.TemplatedThread;


public record LanguageModel(ChatLanguageModel chatLanguageModel) {

    public String RunBlocking(String prompt) {
        return chatLanguageModel.generate(prompt);
    }

    public TemplatedThread<String> RunAsync(String prompt) {
        var t = new TemplatedThread<>(() -> chatLanguageModel.generate(prompt));
        t.runAsync();
        return t;
    }
}
