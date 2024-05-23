package org.okten.carservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.okten.carservice.util.RequestLogger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class CommentMockApiConfiguration {

    @Bean
    public RestTemplateBuilder commentMockApiRestTemplateBuilder() {
        return new RestTemplateBuilder()
                .interceptors(new RequestLogger())
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .rootUri("https://664f8051ec9b4a4a602f020b.mockapi.io/api/v1");
    }

    @Bean
    public RestTemplate commentMockApiRestTemplate() {
        return commentMockApiRestTemplateBuilder().build();
    }

    @Bean
    public WebClient commentMockApiWebClient() {
        return WebClient.builder()
                .baseUrl("https://664f8051ec9b4a4a602f020b.mockapi.io/api/v1")
                .filter(((request, next) -> {
                    log.info("Request url: " + request.url());
                    return next.exchange(request);
                }))
                .build();
    }
}
