package com.poc.cambunda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${tasklist.client.base-url}")
    private String taskApiUrl;

    @Value("${camunda.client.zeebe.base-url}")
    private String baseApiUrl;

    @Bean
    public WebClient tasklistClient() {
        return WebClient.builder()
                .baseUrl(taskApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public WebClient baseClient() {
        return WebClient.builder()
                .baseUrl(baseApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
