package com.poc.langchain.langchain4j.tool;

import com.poc.cambunda.service.DeploymentService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DeploymentTool {

    private final DeploymentService deploymentService;

    public DeploymentTool(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    @Tool("Deploy a BPMN process by file name from classpath")
    public String deployFromClasspath(String connectorName) {
        return deploymentService.deployFromClasspath(connectorName);
    }

    @Tool("Deploy a BPMN process from a remote URL")
    public String deployFromUrl(String fileUrl) {
        return deploymentService.deployFromUrl(fileUrl);
    }

    @Tool("Start a BPMN process with variables")
    public String startProcessWithVariables(String processId, Map<String, Object> variables) {
        return deploymentService.startProcess(processId, variables);
    }

    @Tool("Start a BPMN process by ID")
    public String startProcess(String processId) {
        return deploymentService.startProcess(processId);
    }
}