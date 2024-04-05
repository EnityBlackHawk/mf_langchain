package org.mf.langchain;

import dev.ai4j.openai4j.chat.AssistantMessage;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.mf.langchain.gemini.GeminiChatLanguageModel;
import org.mf.langchain.service.GeminiService;
import org.mf.langchain.util.LanguageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LangchainApplicationTests {

    @Autowired
    private GeminiService service;

    @Test
    void writeAStory() {
        var lm = new LanguageModel(new GeminiChatLanguageModel(service));
        var assistant = AiServices.create(ChatAssistant.class, lm.chatLanguageModel());
        String result = assistant.chat("Write a message to inspire me");
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
