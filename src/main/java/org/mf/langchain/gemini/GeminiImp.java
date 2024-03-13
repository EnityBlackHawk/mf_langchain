package org.mf.langchain.gemini;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;

import java.util.List;

public class GeminiImp implements ChatLanguageModel {
    @Override
    public String generate(String userMessage) {
        return ChatLanguageModel.super.generate(userMessage);
    }

    @Override
    public Response<AiMessage> generate(ChatMessage... messages) {
        return ChatLanguageModel.super.generate(messages);
    }

    @Override
    public Response<AiMessage> generate(List<ChatMessage> list) {
        return null;
    }

    @Override
    public Response<AiMessage> generate(List<ChatMessage> messages, List<ToolSpecification> toolSpecifications) {
        return ChatLanguageModel.super.generate(messages, toolSpecifications);
    }

    @Override
    public Response<AiMessage> generate(List<ChatMessage> messages, ToolSpecification toolSpecification) {
        return ChatLanguageModel.super.generate(messages, toolSpecification);
    }
}
