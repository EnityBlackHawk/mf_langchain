package org.mf.langchain.service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.service.AiServices;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mf.langchain.ChatAssistant;
import org.mf.langchain.DTO.*;
import org.mf.langchain.DataImporter;
import org.mf.langchain.exception.DBConnectionException;
import org.mf.langchain.metadata.DbMetadata;
import org.mf.langchain.metadata.RelationsCardinality;
import org.mf.langchain.prompt.*;
import org.mf.langchain.util.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<Relations> getRelations(String text, @Nullable ChatAssistant gptAssistant) {
        if(gptAssistant == null)
        {
            var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                    .apiKey(System.getenv("GPT_KEY"))
                    .modelName(OpenAiChatModelName.GPT_3_5_TURBO)
                    .maxRetries(1)
                    .logRequests(true)
                    .logResponses(true)
                    //.responseFormat("json_object")
                    .temperature(0.5)
                    .build();
            gptAssistant = AiServices.builder(ChatAssistant.class).chatLanguageModel(gpt).build();
        }

        var q = "Considering this database: \n" + text + " What are the relations between the tables? (Remove duplicates)";
        Type listType = new TypeToken<List<Relations>>() {}.getType();
        String response = gptAssistant.getRelations(q);
        Gson gson = new Gson();
        return gson.fromJson(response, listType);
    }

    public String getRelationsCardinality(String data, Connection connection) {

        var chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(System.getenv("GPT_KEY"))
                .modelName(OpenAiChatModelName.GPT_3_5_TURBO)
                .maxRetries(1)
                .logRequests(true)
                .logResponses(true)
                //.responseFormat("json_object")
                .temperature(0.5)
                .build();
        var gptAssistant = AiServices.builder(ChatAssistant.class).chatLanguageModel(gpt).chatMemory(chatMemory).build();

        var rels = getRelations(data, gptAssistant);
        List<RelationsCardinality> rcd = List.of();

        return gptAssistant.chatAsString("For each relationship: generate SQL queries to identify the max, min and avg number of relationships per id");
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
            card = new QueryResult(spec.cardinality(), "Table name", "Cardinality").asString();
        }
        return Pair.of(data, card);
    }
}
