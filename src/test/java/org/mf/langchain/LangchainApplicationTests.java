package org.mf.langchain;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LangchainApplicationTests {

    @Autowired
    private GeminiService service;

//    @Test
//    void writeAStory() {
//        var lm = new LanguageModel(new GeminiChatLanguageModel(service));
//        var assistant = AiServices.create(ChatAssistant.class, lm.chatLanguageModel());
//        String result = assistant.chat("Write a message to inspire me").content();
//        assertNotNull(result);
//        System.out.println(result);
//    }

    @Test
    void gpt(){
        var gpt = new OpenAiChatModel.OpenAiChatModelBuilder()
                .apiKey(System.getenv("GPT_KEY"))
                .modelName(OpenAiChatModelName.GPT_3_5_TURBO_0125)
                .maxRetries(1)
                .temperature(1.0)
                .maxTokens(500)
                .build();
        String result = gpt.generate("Are you gpt ?");
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    void ImportAirports(){
        String result = DataImporter.Companion.importAirports("C:\\Users\\Luan\\Documents\\mf_langchain\\airports.json", null);
        assertNotNull(result);
        System.out.println(result);
    }


}
