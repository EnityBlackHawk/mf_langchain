package org.mf.langchain;

import org.mf.langchain.gemini.GeminiInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AppConfig {

    @Bean
    public RestClient geminiRestClient(@Value("${gemini.baseUrl}") String baseUrl, @Value("${googleai.api.key}") String apikey) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("x-goog-api-key", apikey)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Bean
    public GeminiInterface geminiInterface(@Qualifier("geminiRestClient") RestClient client) {
        RestClientAdapter adapter = RestClientAdapter.create(client);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(GeminiInterface.class);
    }
}
