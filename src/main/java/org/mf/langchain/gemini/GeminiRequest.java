package org.mf.langchain.gemini;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

public class GeminiRequest {
    public record Root(List<Content> contents) {}
    public record Content(List<Part> parts){}

    @JsonDeserialize(as=TextPart.class)
    public sealed interface Part permits TextPart {
        String text();
    }

    public record TextPart(String text) implements Part {
        public String text() {
            return text;
        }
    }
//    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
//    public record InlineDataPart(InlineData inlineDate) implements Part {}

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public record InlineData(String mimeType, String data) {}
}
