package com.poc.cambunda.service;

import com.poc.cambunda.dto.formDTO.FormResponse;
import com.poc.cambunda.dto.formDTO.FormSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FormService {

    private final WebClient tasklistClient;

    public Mono<FormResponse> getForm(FormSearchRequest formSearchRequest, String authHeader) {
        return tasklistClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forms/{formId}")
                        .queryParam("processDefinitionKey", formSearchRequest.getProcessDefinitionKey())
                        .build(formSearchRequest.getFormId()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authHeader)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class).flatMap(body ->
                                Mono.error(new RuntimeException("❌ 4xx Error while retrieving form: " + body))
                        )
                ).bodyToMono(FormResponse.class);
    }
}