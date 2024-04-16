package org.mf.langchain.gemini;

import okhttp3.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;


public class GeminiHttpClient {

    private String url;
    private String apiKey;
    private String model;

    public static class Builder {
        private String url;
        private String apiKey;
        private String model;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public GeminiHttpClient build() {
            return new GeminiHttpClient(url, apiKey, model);
        }
    }

    private GeminiHttpClient(String url, String apiKey, String model) {
        this.url = url;
        this.apiKey = apiKey;
        this.model = model;
    }

    public String generate(String data) {
        return generate(new GeminiRequest2(List.of(new GeminiRequest2.Content("user", List.of(new GeminiRequest2.Part(data))))));
    }

    public String generate(GeminiRequest2 data) {

        OkHttpClient client = new OkHttpClient();

        Gson gson = new Gson();
        String json = gson.toJson(data);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-goog-api-key", apiKey)
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();
        var call = client.newCall(request);
        try {
            var response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
