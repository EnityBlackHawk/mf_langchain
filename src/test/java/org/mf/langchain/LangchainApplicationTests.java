package org.mf.langchain;

import org.junit.jupiter.api.Test;
import org.mf.langchain.service.GeminiService;
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
        String text = service.getCompletion("What is Lua Script ?");
        assertNotNull(text);
        System.out.println(text);
    }

    @Test
    void ImportAirports(){
        String result = DataImporter.Companion.importAirports("C:\\Users\\Luan\\Documents\\mf_langchain\\airports.json", null);
        assertNotNull(result);
        System.out.println(result);
    }


}
