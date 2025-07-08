package com.poc.cambunda.controller;

import com.poc.cambunda.dto.taskDTO.TaskCompletionRequest;
import com.poc.cambunda.dto.taskDTO.TaskResponse;
import com.poc.cambunda.service.TaskService;
import com.poc.cambunda.service.TokenService;
import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    private final TokenService tokenService;

    private final ZeebeClient client;

    public TaskController(TaskService taskService, TokenService tokenService,  ZeebeClient zeebe) {
        this.taskService = taskService;
        this.tokenService = tokenService;
        this.client = zeebe;
    }

    // We use this when a user completes a form in banking-ws, and we want to inform that a process can move forward.
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
    public ResponseEntity<List<TaskResponse>> getTasks(@RequestParam String assignee) {
        return ResponseEntity.ok(taskService.getTasksByAssignee(assignee, tokenService.getToken()));
    }

    @GetMapping("/taskId")
    public ResponseEntity<TaskResponse> getOneTask(@RequestParam String taskId) {
        return ResponseEntity.ok(taskService.getOneTaskById(taskId, tokenService.getToken()));
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> completeOneTask(@RequestBody TaskCompletionRequest request) {
        taskService.completeTask(request.getTaskId(), request.getVariables(), tokenService.getToken());
        return ResponseEntity.ok().build();
    }
}
