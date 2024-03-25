package org.mf.langchain.service;

import org.mf.langchain.gemini.GeminiInterface;
import org.mf.langchain.gemini.GeminiRequest;
import org.mf.langchain.gemini.GeminiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeminiService {

    public static final String GEMINI_PRO = "gemini-pro";
    public static final String GEMINI_PRO_VISION = "gemini-pro-vision";

    private final GeminiInterface geminiInterface;

    @Autowired
    public GeminiService(GeminiInterface geminiInterface) {
        this.geminiInterface = geminiInterface;
    }

    public GeminiResponse.Root getCompletion(GeminiRequest.Root request) {
        return geminiInterface.getCompletion(GEMINI_PRO, request);
    }

    public GeminiResponse.Root getCompletionWithImage(GeminiRequest.Root request) {
        return geminiInterface.getCompletion(GEMINI_PRO_VISION, request);
    }

    public String getCompletion(String text) {
        GeminiResponse.Root response = getCompletion(new GeminiRequest.Root(
                List.of(new GeminiRequest.Content(List.of(new GeminiRequest.TextPart(text))))));
        return response.candidates().get(0).content().parts().get(0).text();
    }

}
