package org.mf.langchain;

import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.Getter;

@Getter
public record LanguageModel(ChatLanguageModel chatLanguageModel) {

    public String RunBlocking(String prompt) {
        return chatLanguageModel.generate(prompt);
    }

    public TemplatedThread<String> RunAsync(String prompt) {
        var t = new TemplatedThread<String>(() -> {
            return chatLanguageModel.generate(prompt);
        });
        t.runAsync();
        return t;
    }

}
