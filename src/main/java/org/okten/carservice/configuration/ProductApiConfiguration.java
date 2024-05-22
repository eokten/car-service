package org.okten.carservice.configuration;

import lombok.RequiredArgsConstructor;
import org.okten.productservice.ApiClient;
import org.okten.productservice.api.ProductApi;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class ProductApiConfiguration {

    private final TokenPropagationHandler tokenPropagationHandler;

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder()
                .interceptors(tokenPropagationHandler);
    }

    // Variant 1 with http request interceptor
    @Bean
    public ApiClient customApiClient() {
        RestTemplate restTemplate = restTemplateBuilder().build();
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setDebugging(true);
        apiClient.setBasePath("http://localhost:8080/");
        return apiClient;
    }

    // Variant 2 with ApiClient token provider
    @Bean
    public ApiClient apiClientWithAuth() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath("http://localhost:8080/");
        apiClient.setBearerToken(() -> {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            return jwtAuthenticationToken.getToken().getTokenValue();
        });
        apiClient.setMaxAttemptsForRetry(10);
        apiClient.setWaitTimeMillis(1000);
        apiClient.setDebugging(true);
        return apiClient;
    }

    @Bean
    public ProductApi productApi() {
        return new ProductApi(customApiClient());
    }
}
