package org.mf.langchain;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.SystemMessage;

public interface ChatAssistant {

    @SystemMessage("Generate Java files with classes and imports. Each file in a Markdown code block. Explain your reasoning")
    Response<AiMessage> chat (String userMessage);
}
