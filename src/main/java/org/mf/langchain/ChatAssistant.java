package org.mf.langchain;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.SystemMessage;

public interface ChatAssistant {

    @SystemMessage("No explanation, no markdown, only the Java's classes separated by ----- ")
    Response<AiMessage> chat (String userMessage);
}
