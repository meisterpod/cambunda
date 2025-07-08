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

    @Tool(description = "Get a list of tasks assigned to a specific user." +
            "Provide a helpful response about BPMN processes. If your response contains tabular data (like process instances, tasks, or forms), \" +\n" +
            "            \"please format it as COMPLETE and VALID JSON within code blocks like this:  \\\\`\\\\`\\\\`json\\n\" +\n" +
            "            \"      [{\\\"id\\\": \\\"123\\\", \\\"name\\\": \\\"task-name\\\", \\\"processName\\\": \\\"process\\\", \\\"taskState\\\": \\\"CREATED\\\", \\\"creationDate\\\": \\\"2024-01-15T10:30:00Z\\\"}]\\n\" +\n" +
            "            \"      \\\\`\\\\`\\\\`\\n\" +\n" +
            "            \"      Important: \\n\" +\n" +
            "            \"      - Ensure the JSON is complete and properly closed\\n\" +\n" +
            "            \"      - Use ISO date format (YYYY-MM-DDTHH:mm:ssZ) instead of Unix timestamps\\n\" +\n" +
            "            \"      - Include all relevant fields for each record\\n\" +\n" +
            "            \"      This helps me display the information in a readable table format with search and pagination.")
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