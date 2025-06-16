package com.poc.cambunda.service;

import com.poc.cambunda.dto.taskDTO.TaskResponse;
import com.poc.cambunda.dto.taskDTO.TaskSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final WebClient tasklistClient;

    public List<TaskResponse> getTasksByAssignee(String assignee, String authHeader) {
        TaskSearchRequest request = new TaskSearchRequest();
        request.getFilter().setAssignee(assignee);
        request.getFilter().setState("CREATED");
        request.setPageSize(10);

        return tasklistClient.post()
                .uri("/v1/tasks/search")
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TaskResponse>>() {})
                .block();
    }

    public TaskResponse getOneTask(String taskId, String authHeader) {
        TaskSearchRequest request = new TaskSearchRequest();
        request.getFilter().setAssigned(Boolean.TRUE);
        request.getFilter().setState("CREATED");
        request.getFilter().setTaskDefinitionId(taskId);
        return new TaskResponse();
    }
}
