package com.poc.cambunda.service;

import io.camunda.zeebe.client.ZeebeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeploymentService {

    private final ZeebeClient client;

    public String deployFromClasspath(String connectorName) {
        try {
            ClassPathResource resource = new ClassPathResource("bpmn/" + connectorName + ".bpmn");
            if (!resource.exists()) {
                return "❌ File not found in resources: bpmn/" + connectorName;
            }

            var deploymentResult = client.newDeployResourceCommand()
                    .addResourceFromClasspath("bpmn/" + connectorName + ".bpmn")
                    .addResourceFromClasspath("form/approve-form.form")
                    .send()
                    .join();

            return "✅ Deployed successfully: " + connectorName +
                    " (Process Key: " + deploymentResult.getProcesses().get(0).getProcessDefinitionKey() + ")";
        } catch (Exception e) {
            return "❌ Deployment error: " + e.getMessage();
        }
    }

    public String deployFromUrl(String fileUrl) {
        try (InputStream inputStream = new URL(fileUrl).openStream()) {
            String filename = Paths.get(new URI(fileUrl).getPath()).getFileName().toString();

            var deploymentResult = client.newDeployResourceCommand()
                    .addResourceStream(inputStream, filename)
                    .send()
                    .join();

            return "✅ Deployed successfully: " + filename +
                    " (Process Key: " + deploymentResult.getProcesses().get(0).getProcessDefinitionKey() + ")";
        } catch (Exception e) {
            return "❌ Failed to deploy from URL: " + e.getMessage();
        }
    }

    public String startProcess(String processId, Map<String, Object> variables) {
        try {
            var processInstanceResult = client.newCreateInstanceCommand()
                    .bpmnProcessId(processId)
                    .latestVersion()
                    .variables(variables)
                    .send()
                    .join();

            return "✅ Process instance started: " + processId +
                    " (Instance Key: " + processInstanceResult.getProcessInstanceKey() + ")";
        } catch (Exception e) {
            return "❌ Failed to start process: " + e.getMessage();
        }
    }

    public String startProcess(String processId) {
        return startProcess(processId, new HashMap<>());
    }
}
