package org.mf.langchain.gemini;

import java.util.List;

public record GeminiResponse2(List<Candidate> candidates) {

    public record Candidate(Content content, String finishReason, int index, List<SafetyRating> safetyRatings) {}

    public  record Content(List<Part> parts, String role) {}

    public record Part(String text) {}

    public record SafetyRating(String category, String probability) {}
}
