package com.poc.cambunda.controller;

import com.poc.cambunda.dto.taskDTO.TaskResponse;
import com.poc.cambunda.service.TaskService;
import io.camunda.zeebe.client.ZeebeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Autowired
    private ZeebeClient client;

    // We use this when a user completes a form in banking-ws, and we want to inform that a process can
    // move forward.
    @PostMapping("/message")
    public ResponseEntity<Void> publishMessage(@RequestBody Map<String, Object> payload) {
        client.newPublishMessageCommand()
                .messageName((String) payload.get("messageName"))
                .correlationKey((String) payload.get("correlationKey"))
                .variables(payload)
                .send()
                .join();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam String assignee,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return ResponseEntity.ok(taskService.getTasksForAssignee(assignee, authHeader));
    }
}
