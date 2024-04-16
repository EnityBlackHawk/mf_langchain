package org.mf.langchain.gemini;

import dev.ai4j.openai4j.chat.AssistantMessage;
import dev.ai4j.openai4j.chat.SystemMessage;
import dev.ai4j.openai4j.chat.UserMessage;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.InternalOpenAiHelper;
import dev.langchain4j.model.output.Response;

import java.util.ArrayList;
import java.util.List;



public class GeminiChatLanguageModel implements ChatLanguageModel {

    private final GeminiHttpClient service;

    public GeminiChatLanguageModel(GeminiHttpClient service){
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
        var contents = new ArrayList<GeminiRequest2.Content>();
        InternalOpenAiHelper.toOpenAiMessages(list).stream().forEach((e) -> {
            switch (e.role()){
                case USER -> {
                      contents.add(
                              new GeminiRequest2.Content(
                                      "user",
                                      List.of(
                                              new GeminiRequest2.Part((String)((UserMessage)e).content())
                                      )
                              )
                      );
                }
                case SYSTEM -> {
                    contents.add(
                            new GeminiRequest2.Content(
                                    "system",
                                    List.of(
                                            new GeminiRequest2.Part((String)((SystemMessage)e).content())
                                    )
                            )
                    );
                }
                case ASSISTANT -> {
                    contents.add(
                            new GeminiRequest2.Content(
                                    "model",
                                    List.of(
                                            new GeminiRequest2.Part((String)((AssistantMessage)e).content())
                                    )
                            )
                    );
                }
            }
            
        });
        var result = service.generate(
                new GeminiRequest2(contents)
        );
        return Response.from(AiMessage.from(result.candidates().get(0).content().parts().get(0).text()));
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
