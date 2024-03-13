package org.mf.langchain.gemini;

import java.util.List;

public class GeminiResponse {

    public record Root(List<Candidate> candidates, PromptFeedback promptFeedback) {
        public record Candidate(GeminiRequest.Content content, String finishReason, int index, List<SafetyRating> safetyRatings) {
            public record Content(List<GeminiRequest.TextPart> parts, String role){}
        }
    }

    public record SafetyRating(String category, String probability) {}
    public record PromptFeedback(List<SafetyRating> safetyRatings) {}

}
