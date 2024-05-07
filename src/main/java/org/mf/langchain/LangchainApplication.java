package org.mf.langchain;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.service.AiServices;
import org.mf.langchain.gemini.GeminiChatLanguageModel;
import org.mf.langchain.gemini.GeminiHttpClient;
import org.mf.langchain.metadata.DbMetadata;
import org.mf.langchain.prompt.Framework;
import org.mf.langchain.prompt.MigrationPreferences;
import org.mf.langchain.prompt.PrompData;
import org.mf.langchain.prompt.Query;
import org.mf.langchain.repositories.AirlineRepository;
import org.mf.langchain.repositories.AirportRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
public class LangchainApplication {

    public static final boolean RUN_CACHED = true;


    public static void main(String[] args) throws IOException, SQLException {
        var context = SpringApplication.run(LangchainApplication.class, args);

        var airportRepo = context.getBean(AirportRepository.class);
        var airlinesRepo = context.getBean(AirlineRepository.class);

        DataImporter.Companion.importAirports("/home/luan/Documents/mf_langchain/airports.json", airportRepo);
        DataImporter.Companion.importAirlines("/home/luan/Documents/mf_langchain/airlines.json", airlinesRepo);


        var dbc = new DbMetadata("jdbc:h2:mem:testdb", "sa", "password", "TB_%");
        DataImporter.Companion.runSQL("/home/luan/Documents/mf_langchain/inserts.sql", dbc.getConnection());
        PrompData pd = new PrompData(dbc,
                MigrationPreferences.PREFER_PERFORMANCE,
                Framework.SPRING_DATA,
                Query.from("/home/luan/Documents/mf_langchain/most_used_selects.sql"));


        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        GeminiHttpClient gs = new GeminiHttpClient.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent")
                .apiKey(System.getenv("GOOGLEAI_API_KEY"))
                .build();

        var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(System.getenv("GPT_KEY"))
                .modelName(OpenAiChatModelName.GPT_3_5_TURBO_0125)
                .maxRetries(1)
                .temperature(1.0)
                .build();

        var assintant = AiServices.builder(ChatAssistant.class).chatLanguageModel(gpt).chatMemory(chatMemory).build();
        String finalResult = RUN_CACHED ? ConvertToJavaFile.MOCK_2 : null;
//        while (!RUN_CACHED && pd.hasNext()) {
//            var x = pd.next();
//            System.out.println(x);
//            var result = assintant.chat(x);
//            System.out.println(result.content().text());
//            finalResult = result.content().text();
//        }

        if(!RUN_CACHED) {
            var x = pd.get();
            System.out.println(x);
            var result = assintant.chat(x);
            System.out.println(result.content().text());
            finalResult = result.content().text();
        }

        ConvertToJavaFile.toFile("/home/luan/Documents/mf_langchain/src/main/java/org/mf/langchain/auto/", finalResult);

    }
}
