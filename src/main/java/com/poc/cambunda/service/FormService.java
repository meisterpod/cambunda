package com.poc.cambunda.service;

import com.poc.cambunda.dto.formDTO.FormSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FormService {
    private final WebClient tasklistClient;

    public Mono<ResponseEntity<String>> getForm(FormSearchRequest formSearchRequest, String authHeader) {
        return tasklistClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forms/{formId}")
                        .queryParam("processDefinitionKey", formSearchRequest.getProcessDefinitionKey())
                        .build(formSearchRequest.getFormId()))
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .retrieve()
                .toEntity(String.class);
    }
}
