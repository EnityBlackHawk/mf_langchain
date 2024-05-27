package org.mf.langchain.service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.AiServices;
import org.mf.langchain.ChatAssistant;
import org.mf.langchain.DTO.MfResponse;
import org.mf.langchain.DTO.SpecificationDTO;
import org.mf.langchain.DTO.TestResultDTO;
import org.mf.langchain.model.Specification;
import org.mf.langchain.model.TestResult;
import org.mf.langchain.prompt.Framework;
import org.mf.langchain.prompt.MigrationPreferences;
import org.mf.langchain.prompt.PrompData;
import org.mf.langchain.prompt.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LLMService {

    private final PersistenceService persistenceService;

    public LLMService(@Autowired PersistenceService persistenceService){
        this.persistenceService = persistenceService;
    }

    public MfResponse Generate(SpecificationDTO spec) {

        var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(System.getenv("GPT_KEY"))
                .modelName(spec.LLM())
                .maxRetries(1)
                .temperature(0.5)
                .build();
        var gptAssistant = AiServices.builder(ChatAssistant.class).chatLanguageModel(gpt).build();

        var prompt = new PrompData(spec.data_source(),
                spec.prioritize_performance() ? MigrationPreferences.PREFER_PERFORMANCE : MigrationPreferences.PREFER_CONSISTENCY,
                spec.allow_ref(),
                Framework.valueOf(spec.framework()),
                spec.workload().stream().map(Query::new).toList(),
                spec.custom_prompt()
                );
        var result = gptAssistant.chat(prompt.get());
        persistenceService.persist(
                new TestResultDTO(
                        prompt.get(),
                        spec,
                        result.content().text(),
                        result.tokenUsage().totalTokenCount()
                )
        );
        return new MfResponse(result.content().text(), result.tokenUsage().totalTokenCount(), prompt.get(), new Date());
    }


}
