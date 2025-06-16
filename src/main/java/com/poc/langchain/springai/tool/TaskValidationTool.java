package com.poc.langchain.springai.tool;

import com.poc.cambunda.dto.taskDTO.TaskResponse;
import com.poc.cambunda.service.TaskService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskValidationTool {

    private final TaskService taskService;

    public TaskValidationTool(TaskService taskService) {
        this.taskService = taskService;
    }

    @Tool(name = "getTime", description = "Returns the current server time")
    public String getTime() {
        return LocalDateTime.now().toString();
    }

    @Tool(description = "Validate a task based on the id")
    public TaskResponse validateTask(@ToolParam String idTask) {
        return taskService.getOneTask(idTask, "authHeader");
    }
}