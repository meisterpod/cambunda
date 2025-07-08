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

    @Tool(description = "Search for process definitions with optional filters. " +
            "Provide a helpful response about BPMN processes. If your response contains tabular data (like process instances, tasks, or forms), " +
            "please format it as COMPLETE and VALID JSON within code blocks like this:  \\`\\`\\`json\n" +
            "      [{\"id\": \"123\", \"name\": \"task-name\", \"processName\": \"process\", \"taskState\": \"CREATED\", \"creationDate\": \"2024-01-15T10:30:00Z\"}]\n" +
            "      \\`\\`\\`\n" +
            "      Important: \n" +
            "      - Ensure the JSON is complete and properly closed\n" +
            "      - Use ISO date format (YYYY-MM-DDTHH:mm:ssZ) instead of Unix timestamps\n" +
            "      - Include all relevant fields for each record\n" +
            "      This helps me display the information in a readable table format with search and pagination.")
    public ProcessDefinitionSearchResponse searchProcessDefinitions(@ToolParam ProcessDefinitionSearchRequest request) {
        return processService.searchProcessDefinitions(request, tokenService.getToken());
    }

    @Tool(description = "Search for process instances with optional filters" +
            "Provide a helpful response about BPMN processes. If your response contains tabular data (like process instances, tasks, or forms), \" +\n" +
            "            \"please format it as COMPLETE and VALID JSON within code blocks like this:  \\\\`\\\\`\\\\`json\\n\" +\n" +
            "            \"      [{\\\"id\\\": \\\"123\\\", \\\"name\\\": \\\"task-name\\\", \\\"processName\\\": \\\"process\\\", \\\"taskState\\\": \\\"CREATED\\\", \\\"creationDate\\\": \\\"2024-01-15T10:30:00Z\\\"}]\\n\" +\n" +
            "            \"      \\\\`\\\\`\\\\`\\n\" +\n" +
            "            \"      Important: \\n\" +\n" +
            "            \"      - Ensure the JSON is complete and properly closed\\n\" +\n" +
            "            \"      - Use ISO date format (YYYY-MM-DDTHH:mm:ssZ) instead of Unix timestamps\\n\" +\n" +
            "            \"      - Include all relevant fields for each record\\n\" +\n" +
            "            \"      This helps me display the information in a readable table format with search and pagination.")
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
