package com.poc.cambunda.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.cambunda.dto.taskDTO.TaskResponse;
import com.poc.cambunda.dto.taskDTO.TaskSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final WebClient tasklistClient;

    public List<TaskResponse> getTasksByAssignee(String assignee, String authHeader) {
        TaskSearchRequest request = new TaskSearchRequest();
        request.getFilter().setAssignee(assignee);
        request.getFilter().setTaskState("CREATED");
        request.getFilter().setAssigned(Boolean.TRUE);
        request.setPageSize(10);

        return tasklistClient.post()
                .uri("/v1/tasks/search")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authHeader)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TaskResponse>>() {})
                .block();
    }

    public TaskResponse getOneTaskById(String taskId, String authHeader) {
        TaskSearchRequest request = new TaskSearchRequest();
        request.getFilter().setTaskDefinitionId(taskId);
        request.setPageSize(1);
        List<TaskResponse> tasks = tasklistClient.post()
                .uri("/v1/tasks/search")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authHeader)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TaskResponse>>() {})
                .block();

        return tasks != null && !tasks.isEmpty() ? tasks.get(0) : null;
    }


    public void completeTask(String taskId, Map<String, Object> variables, String authHeader) {
        List<Map<String, Object>> formattedVariables = variables.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> var = new LinkedHashMap<>();
                    var.put("name", entry.getKey());
                    var.put("value", entry.getValue());
                    return var;
                })
                .toList();

        Map<String, Object> requestBody = Map.of("variables", formattedVariables);

        try {
            String json = new ObjectMapper().writeValueAsString(requestBody);
            System.out.println("Sending to Camunda Tasklist: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        tasklistClient.patch()
                .uri("/v1/tasks/{taskId}/complete", taskId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
