package org.mf.langchain.gemini;


import lombok.Data;

import java.util.List;


public class GeminiRequest2 {
    private List<Content> contents;

    public static record Content(String role, List<Part> parts) {}
    public static record Part(String text) {}

    public GeminiRequest2(List<Content> contents){
        this.contents = contents;
    }

}
