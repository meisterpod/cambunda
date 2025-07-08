package com.poc.langchain.springai.tool;

import com.poc.cambunda.service.DeploymentService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class DeployProcessTool {

    private final DeploymentService deploymentService;

    public DeployProcessTool(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    @Tool(name = "getTime", description = "Returns the current server time")
    public String getTime() {
        return LocalDateTime.now().toString();
    }

    @Tool(description = "Deploy a BPMN process by file name from classpath")
    public String deployFromClasspath(@ToolParam String connectorName) {
        return deploymentService.deployFromClasspath(connectorName);
    }

    @Tool(description = "Deploy a BPMN process from a remote URL")
    public String deployFromUrl(String fileUrl) {
        return deploymentService.deployFromUrl(fileUrl);
    }

    @Tool(description = "Start a BPMN process with variables")
    public String startProcessWithVariables(String processId, Map<String, Object> variables) {
        return deploymentService.startProcess(processId, variables);
    }

    @Tool(description = "Start a BPMN process by ID")
    public String startProcess(@ToolParam String processId) {
        return deploymentService.startProcess(processId);
    }
}