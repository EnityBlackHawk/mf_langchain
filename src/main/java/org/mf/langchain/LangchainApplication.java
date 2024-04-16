package org.mf.langchain;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.mf.langchain.gemini.GeminiChatLanguageModel;
import org.mf.langchain.metadata.DbMetadata;
import org.mf.langchain.repositories.AirlineRepository;
import org.mf.langchain.repositories.AirportRepository;
import org.mf.langchain.service.GeminiService;
import org.mf.langchain.util.SqlDataType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class LangchainApplication {


    public static void main(String[] args) throws IOException, SQLException {
        var context = SpringApplication.run(LangchainApplication.class, args);

        var airportRepo = context.getBean(AirportRepository.class);
        var airlinesRepo = context.getBean(AirlineRepository.class);

        DataImporter.Companion.importAirports("/home/luan/Documents/mf_langchain/airports.json", airportRepo);
        DataImporter.Companion.importAirlines("/home/luan/Documents/mf_langchain/airlines.json", airlinesRepo);


        var dbc = new DbMetadata("jdbc:h2:mem:testdb", "sa", "password", "TB_%");
        String s = "";
        for(var x  : dbc.getTables()){
            s = s.concat(x.toString() + "\n");
        }

        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        GeminiService gs = context.getBean(GeminiService.class);
        var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(System.getenv("GPT_KEY"))
                .modelName(OpenAiChatModelName.GPT_3_5_TURBO_0125)
                .maxRetries(1)
                .temperature(1.0)
                .build();
        var assintant = AiServices.builder(ChatAssistant.class).chatLanguageModel(new GeminiChatLanguageModel(gs)).chatMemory(chatMemory).build();
        var pB = new PrompBuider(dbc, PrompBuider.StructureOptions.PREFER_CONSISTENCY);
        while (pB.hasNext()) {
            var x = pB.next();
            System.out.println(x);
            var result = assintant.chat(x);
            System.out.println(result.content().text());
        }
    }
}
