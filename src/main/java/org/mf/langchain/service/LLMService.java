package org.mf.langchain.service;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mf.langchain.ChatAssistant;
import org.mf.langchain.DTO.MfResponse;
import org.mf.langchain.DTO.SpecificationDTO;
import org.mf.langchain.DTO.TestResultDTO;
import org.mf.langchain.DataImporter;
import org.mf.langchain.exception.DBConnectionException;
import org.mf.langchain.metadata.DbMetadata;
import org.mf.langchain.prompt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;

@Service
public class LLMService {

    private final PersistenceService persistenceService;

    public LLMService(@Autowired PersistenceService persistenceService){
        this.persistenceService = persistenceService;
    }

    public MfResponse Generate(SpecificationDTO spec) {

        var data = getData(spec);

        var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(System.getenv("GPT_KEY"))
                .modelName(spec.LLM())
                .maxRetries(1)
                .temperature(0.5)
                .build();
        var gptAssistant = AiServices.builder(ChatAssistant.class).chatLanguageModel(gpt).build();

        var prompt = new PromptData2(
                        data.getFirst(),
                        spec.prioritize_performance() ? MigrationPreferences.PREFER_PERFORMANCE : MigrationPreferences.PREFER_CONSISTENCY,
                        spec.allow_ref(),
                        Framework.valueOf(spec.framework()),
                        data.getSecond(),
                        true,
                        spec.workload().stream().map(Query::new).toList(),
                        spec.custom_prompt()
                        );
        System.out.println(prompt.get());
        var result = gptAssistant.chat(prompt.get());
        persistenceService.persist(
                new TestResultDTO(
                        prompt.get(),
                        SpecificationDTO.overrideDataSource(spec, data.getFirst()),
                        result.content().text(),
                        result.tokenUsage().totalTokenCount()
                )
        );
        return new MfResponse(result.content().text(), result.tokenUsage().totalTokenCount(), prompt.get(), new Date());
    }

    private Pair<String, String> getData(SpecificationDTO spec) {
        String data = "";
        String card = null;

        if(spec.data_source() == null) {
            
            if(spec.credentials() == null)
                throw new RuntimeException("Credentials are required when not proving data source");
            
            if(spec.credentials().connectionString() == null) 
                throw new RuntimeException("credentials.connectionString is required when not proving data source");
            
            var cred = spec.credentials();
            var user = cred.username() != null ? cred.username() : "admin";
            var passw = cred.password() != null ? cred.password() : "admin";
            try {
                var mdb = new DbMetadata(
                        spec.credentials().connectionString(),
                        user,
                        passw,
                        null);

                if(!mdb.isConnected())
                    throw new DBConnectionException();
                data = mdb.toString();
                card = DataImporter.Companion.getCardinality(mdb.getConnection());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            data = spec.data_source();
        }
        return Pair.of(data, card);
    }
}
