package org.mf.langchain;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;
import dev.langchain4j.model.localai.LocalAiChatModel;
import dev.langchain4j.model.localai.LocalAiStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.openai.OpenAiLanguageModel;
import dev.langchain4j.model.openai.OpenAiLanguageModelName;
import dev.langchain4j.service.AiServices;
import org.mf.langchain.service.LangChainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Scanner;

@SpringBootApplication
public class LangchainApplication {

    public static void main(String[] args) throws IOException {
        var context = SpringApplication.run(LangchainApplication.class, args);

//        StreamingChatLanguageModel model = LocalAiStreamingChatModel.builder()
//                .modelName("phi-2")
//                .baseUrl("http://localhost:8080")
//                .timeout(Duration.ofDays(1))
//                .temperature(0.8)
//                .build();
//        Assistant assistant = AiServices.create(Assistant.class, model);
//
//        var ts = assistant.chat("What is Lua Script ?");
//        ts.onNext(System.out::print).onError(Throwable::printStackTrace).start();

    }
}
