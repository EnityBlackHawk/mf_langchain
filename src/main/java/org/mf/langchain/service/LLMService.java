package org.mf.langchain.service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.AiServices;
import org.mf.langchain.ChatAssistant;
import org.mf.langchain.DTO.MfResponse;
import org.mf.langchain.DTO.SpecificationDTO;
import org.mf.langchain.prompt.Framework;
import org.mf.langchain.prompt.MigrationPreferences;
import org.mf.langchain.prompt.PrompData;
import org.mf.langchain.prompt.Query;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LLMService {

    private final ChatAssistant gptAssistant;

    LLMService() {
        var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(System.getenv("GPT_KEY"))
                .modelName(OpenAiChatModelName.GPT_3_5_TURBO_0125)
                .maxRetries(1)
                .temperature(1.0)
                .build();
        gptAssistant = AiServices.builder(ChatAssistant.class).chatLanguageModel(gpt).build();
    }

    public MfResponse Generate(SpecificationDTO spec) {
        var prompt = new PrompData(spec.data_source(),
                spec.prioritize_performance() ? MigrationPreferences.PREFER_PERFORMANCE : MigrationPreferences.PREFER_CONSISTENCY,
                Framework.valueOf(spec.framework()),
                spec.workload().stream().map(o -> new Query(o.query())).toList());
        var result = gptAssistant.chat(prompt.get());
        return new MfResponse(result.content().text(), result.tokenUsage().totalTokenCount(), new Date());
    }

    public Response<AiMessage> Generate(String prompt) {
        return gptAssistant.chat(prompt);
    }


}
