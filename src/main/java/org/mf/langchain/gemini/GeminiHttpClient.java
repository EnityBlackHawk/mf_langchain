package org.mf.langchain.gemini;

import okhttp3.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


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

    public GeminiResponse2 generate(String data) {
        return generate(new GeminiRequest2(List.of(new GeminiRequest2.Content("user", List.of(new GeminiRequest2.Part(data))))));
    }

    public GeminiResponse2 generate(GeminiRequest2 data) {

        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(1, TimeUnit.HOURS)
                .connectTimeout(1, TimeUnit.HOURS)
                .readTimeout(1, TimeUnit.HOURS)
                .writeTimeout(1, TimeUnit.HOURS)
                .build();



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
            return gson.fromJson(response.body().string(), GeminiResponse2.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
