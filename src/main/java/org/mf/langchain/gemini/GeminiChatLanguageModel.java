package org.mf.langchain.gemini;

import dev.ai4j.openai4j.chat.ChatCompletionRequest;
import dev.ai4j.openai4j.chat.SystemMessage;
import dev.ai4j.openai4j.chat.UserMessage;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.InternalOpenAiHelper;
import dev.langchain4j.model.output.Response;
import lombok.Builder;
import org.mf.langchain.service.GeminiService;

import java.util.List;

import static dev.ai4j.openai4j.chat.Role.USER;


public class GeminiChatLanguageModel implements ChatLanguageModel {

    private final GeminiService service;

    public GeminiChatLanguageModel(GeminiService service){
        this.service = service;
    }

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
        var x = InternalOpenAiHelper.toOpenAiMessages(list).stream().map((e) -> {
            switch (e.role()){
                case USER -> {
                    return (String)((UserMessage)e).content();
                }
                case SYSTEM -> {
                    return ((SystemMessage)e).content();
                }
                default -> {
                    return "";
                }
            }
            
        });
        var result = service.getCompletion(
                x.reduce("", (msg, unit) -> msg + " " + unit)
        );
        return Response.from(AiMessage.from(result));
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
