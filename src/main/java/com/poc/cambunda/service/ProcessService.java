package com.poc.cambunda.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.cambunda.dto.processDTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final WebClient operateClient;

    public ProcessDefinitionSearchResponse searchProcessDefinitions(ProcessDefinitionSearchRequest request, String authHeader) {
        try {
            System.out.println("Recherche des définitions de processus avec les critères: {}");

            return operateClient.post()
                    .uri("/v1/process-definitions/search")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authHeader)
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .bodyValue(request)
                    .exchangeToMono(response -> {
                        // Log HTTP status
                        System.out.println("Status: " + response.statusCode());
                        return response.bodyToMono(String.class)
                                .doOnNext(rawBody -> System.out.println("Raw response body: " + rawBody))
                                .map(rawBody -> {
                                    try {
                                        ObjectMapper mapper = new ObjectMapper();
                                        return mapper.readValue(rawBody, ProcessDefinitionSearchResponse.class);
                                    } catch (Exception e) {
                                        throw new RuntimeException("Error parsing response body", e);
                                    }
                                });
                    })
                    .block();

        } catch (Exception e) {
            System.out.println("Erreur lors de l'appel à l'API Operate");
            throw new RuntimeException("Impossible de récupérer les définitions de processus", e);
        }
    }

    public ProcessInstanceSearchResponse searchProcessInstances(ProcessInstanceSearchRequest request, String authHeader) {
        try {
            return operateClient.post()
                    .uri("/v1/process-instances/search")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authHeader)
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .bodyValue(request)
                    .exchangeToMono(response -> {
                        // Log HTTP status
                        System.out.println("Status: " + response.statusCode());
                        return response.bodyToMono(String.class)
                                .doOnNext(rawBody -> System.out.println("Raw response body: " + rawBody))
                                .map(rawBody -> {
                                    try {
                                        ObjectMapper mapper = new ObjectMapper();
                                        System.out.println("Payload to Operate API: " + new ObjectMapper().writeValueAsString(request));
                                        return mapper.readValue(rawBody, ProcessInstanceSearchResponse.class);
                                    } catch (Exception e) {
                                        throw new RuntimeException("Error parsing response body", e);
                                    }
                                });
                    })
                    .block();
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche des instances de processus");
            throw new RuntimeException("Impossible de récupérer les instances de processus", e);
        }
    }


    public ProcessDefinitionResponse getProcessDefinitionById(String processDefinitionId, String authHeader) {
        try {
            return operateClient.get()
                    .uri("/v1/process-definitions/{id}", processDefinitionId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authHeader)
                    .retrieve()
                    .bodyToMono(ProcessDefinitionResponse.class)
                    .block();

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de la définition de processus {}" + processDefinitionId);
            throw new RuntimeException("Impossible de récupérer la définition de processus", e);
        }
    }

    public ProcessInstanceResponse getProcessInstanceById(String processInstanceId, String authHeader) {
        try {
            return operateClient.get()
                    .uri("/v1/process-instances/{id}", processInstanceId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authHeader)
                    .retrieve()
                    .bodyToMono(ProcessInstanceResponse.class)
                    .block();

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de l'instance de processus {}" + processInstanceId);
            throw new RuntimeException("Impossible de récupérer l'instance de processus", e);
        }
    }

    public String deleteProcessInstanceById(String processInstanceId, String authHeader) {
        try {
            return operateClient.delete()
                    .uri("/v1/process-instances/{key}", processInstanceId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authHeader)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression de l'instance de processus {}" + processInstanceId);
            throw new RuntimeException("Impossible de supprimer l'instance de processus", e);
        }
    }
}
