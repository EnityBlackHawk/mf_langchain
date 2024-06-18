package org.mf.langchain.service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.service.AiServices;
import org.jetbrains.annotations.Nullable;
import org.mf.langchain.ChatAssistant;
import org.mf.langchain.DTO.*;
import org.mf.langchain.DataImporter;
import org.mf.langchain.exception.DBConnectionException;
import org.mf.langchain.metadata.Column;
import org.mf.langchain.metadata.DbMetadata;
import org.mf.langchain.DTO.RelationsCardinalityDTO;
import org.mf.langchain.metadata.Table;
import org.mf.langchain.prompt.*;
import org.mf.langchain.util.QueryResult;
import org.mf.langchain.util.TemplatedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
                        data.getSecond().toString(),
                        true,
                        spec.workload().stream().map(Query::new).toList(),
                        spec.custom_prompt()
                        );
        System.out.println(prompt.get());

        //var result = gptAssistant.chat(prompt.get());
        var result = "Response";

        persistenceService.persist(
                new TestResultDTO(
                        prompt.get(),
                        SpecificationDTO.overrideCardinality(SpecificationDTO.overrideDataSource(spec, data.getFirst()), data.getSecond()),
                        result, //result.content().text(),
                        0 // result.tokenUsage().totalTokenCount()
                )
        );
        return new MfResponse(
                result, //result.content().text(),
                0, //result.tokenUsage().totalTokenCount(),
                prompt.get(),
                new Date());
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

    public List<RelationsCardinalityDTO> getRelationsCardinality(DbMetadata metadata) {

        var queries = new ArrayList<Pair<Relations, String>>();

        var rels = getRelations(metadata.toString(), null);
        //var rels = List.of(new Relations("aircraft", "airline", "many-to-one"));
        List<RelationsCardinalityDTO> rcd = new ArrayList<>();

        var templateString = new TemplatedString(
                        "SELECT {{target}}.{{target_pk}} AS id , " +
                        "COUNT({{source}}.{{source_pk}}) AS number_of_{{source}} " +
                        "FROM {{source}} " +
                        "JOIN {{target}} ON {{source}}.{{prop}} = {{target}}.{{target_pk}} " +
                        "GROUP BY {{target}}.{{target_pk}}"
        );

        for(var rel : rels) {

            if(rel.cardinality.equals("one-to-many")) continue;
            if(rel.table_source.equals(rel.table_target)) continue;

            Table tSource = metadata.getTables().stream().filter((e) -> Objects.equals(e.name(), rel.table_source))
                    .findFirst().orElseThrow(RuntimeException::new);
            List<Column.FkInfo> props = tSource.columns().stream().filter((e) -> e.isFk() && Objects.equals(e.fkInfo().pk_tableName(), rel.table_target))
                    .map(Column::fkInfo).toList();

            if(props.isEmpty()) throw new RuntimeException("No foreign key found");

            queries.addAll(
                    props.stream().map((e) -> Pair.of(rel, templateString.render(
                            Pair.of("source", rel.table_source),
                            Pair.of("target", rel.table_target),
                            Pair.of("target_pk", e.pk_name()),
                            Pair.of("source_pk", tSource.getPrimaryKey().name()),
                            Pair.of("prop", e.columnName()))
                    )).toList()
            );
        }

        for(var q : queries) {
            var qResult =
                    DataImporter.Companion.runQuery(q.getSecond(), metadata.getConnection(), QueryResult.class);
            var values = qResult.getAllFromColumn("number_of_" + q.getFirst().table_source, Integer.class);
            int min = values.stream().min(Integer::compareTo).orElseThrow(RuntimeException::new);
            int max = values.stream().max(Integer::compareTo).orElseThrow(RuntimeException::new);
            double avg = values.stream().mapToInt(Integer::intValue).average().orElseThrow();
            rcd.add(new RelationsCardinalityDTO(q.getFirst(), min, max, avg));
        }

        return rcd;
    }

    private Pair<String, List<RelationsCardinalityDTO>> getData(SpecificationDTO spec) {
        String data = "";
        List<RelationsCardinalityDTO> card = null;

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
                //card = DataImporter.Companion.getCardinality(mdb.getConnection());
                card = getRelationsCardinality(mdb);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            data = spec.data_source();
            card = spec.cardinality();
        }
        if(card == null) throw new RuntimeException("Cardinality was null");
        return Pair.of(data, card);
    }
}
