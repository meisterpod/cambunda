package com.poc.langchain.springai.tool;

import com.poc.cambunda.dto.taskDTO.TaskResponse;
import com.poc.cambunda.service.TaskService;
import com.poc.cambunda.service.TokenService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TaskProcessTool {

    private final TaskService taskService;
    private final TokenService tokenService;


    public TaskProcessTool(TaskService taskService, TokenService tokenService) {
        this.tokenService = tokenService;
        this.taskService = taskService;
    }

    @Tool(description = "Search a task by its ID and return details")
    public TaskResponse searchTask(
            @ToolParam(description = "The ID of the task") String taskId) {
        return taskService.getOneTaskById(taskId, tokenService.getToken());
    }

    @Tool(description = "Get a list of tasks assigned to a specific user")
    public List<TaskResponse> getTasksByAssignee(
            @ToolParam(description = "The assignee username or ID") String assignee) {
        return taskService.getTasksByAssignee(assignee, tokenService.getToken());
    }

    @Tool(description = "Complete a task by ID and provide input variables")
    public void completeTask(
            @ToolParam(description = "The ID of the task to complete") String taskId,
            @ToolParam(description = "A JSON map of variables to send for completion, e.g., {\"approvalDecision\":\"approve\"}") Map<String, Object> variables) {
        taskService.completeTask(taskId, variables, tokenService.getToken());
    }
}