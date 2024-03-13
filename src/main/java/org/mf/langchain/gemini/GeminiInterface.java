package org.mf.langchain.gemini;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/models/")
public interface GeminiInterface {
    @PostExchange("{model}:generateContent")
    GeminiResponse.Root getCompletion(
            @PathVariable String model,
            @RequestBody GeminiRequest.Root request
    );
}
