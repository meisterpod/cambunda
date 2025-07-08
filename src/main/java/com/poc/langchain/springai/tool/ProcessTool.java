package com.poc.langchain.springai.tool;

import com.poc.cambunda.dto.processDTO.*;
import com.poc.cambunda.service.ProcessService;
import com.poc.cambunda.service.TokenService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class ProcessTool {

    private final ProcessService processService;
    private final TokenService tokenService;

    public ProcessTool(ProcessService processService, TokenService tokenService) {
        this.processService = processService;
        this.tokenService = tokenService;
    }

    @Tool(description = "Search for process definitions with optional filters")
    public ProcessDefinitionSearchResponse searchProcessDefinitions(@ToolParam ProcessDefinitionSearchRequest request) {
        return processService.searchProcessDefinitions(request, tokenService.getToken());
    }

    @Tool(description = "Search for process instances with optional filters")
    public ProcessInstanceSearchResponse searchProcessInstances(@ToolParam ProcessInstanceSearchRequest request) {
        return processService.searchProcessInstances(request, tokenService.getToken());
    }

    @Tool(description = "Get a process definition by its ID")
    public ProcessDefinitionResponse getProcessDefinitionById(@ToolParam String id) {
        return processService.getProcessDefinitionById(id, tokenService.getToken());
    }

    @Tool(description = "Get a process instance by its ID")
    public ProcessInstanceResponse getProcessInstanceById(@ToolParam String id) {
        return processService.getProcessInstanceById(id, tokenService.getToken());
    }

    @Tool(description = "Delete a process instance by its ID")
    public String deleteProcessInstanceById(@ToolParam String id) {
        return processService.deleteProcessInstanceById(id, tokenService.getToken());
    }
}
