package org.mf.langchain;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import org.mf.langchain.DTO.Relations;

import java.util.List;

public interface ChatAssistant {

    //@SystemMessage("Generate Java files with classes and imports. Each file in a Markdown code block. Explain your reasoning")
    Response<AiMessage> chat (String userMessage);
    @SystemMessage("Using this JSON format: [{table_source: String, table_target: String, cardinality: String}, ...]")
    String getRelations(String text);
}
