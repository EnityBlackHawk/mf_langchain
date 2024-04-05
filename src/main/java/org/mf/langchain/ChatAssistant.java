package org.mf.langchain;

import dev.langchain4j.service.SystemMessage;

public interface ChatAssistant {

    @SystemMessage("End the message saying good bye :)")
    String chat (String userMessage);
}
