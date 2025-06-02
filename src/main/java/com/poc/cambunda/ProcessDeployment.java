package com.poc.cambunda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import java.io.InputStream;
import java.net.URL;
import java.net.URI;
import java.nio.file.Paths;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process")
public class ProcessDeployment {

	@Autowired
	private ZeebeClient client;

	@PostMapping("/deploy")
	public ResponseEntity<String> deployFromClasspath(@RequestBody Map<String, String> requestBody) {
		try {
			String bpmnFilename = requestBody.get("connectorName");
			ClassPathResource resource = new ClassPathResource("bpmn/" + bpmnFilename + ".bpmn");
			if (!resource.exists()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("❌ File not found in resources: bpmn/" + bpmnFilename);
			}

			// Deploy the process definition
			var deploymentResult = client.newDeployResourceCommand()
					.addResourceFromClasspath("bpmn/" + bpmnFilename + ".bpmn")
					.send()
					.join();

			return ResponseEntity.ok("✅ Deployed successfully: " + bpmnFilename +
					" (Process Key: " + deploymentResult.getProcesses().get(0).getProcessDefinitionKey() + ")");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("❌ Deployment error: " + e.getMessage());
		}
	}

	@PostMapping("/deploy/url")
	public ResponseEntity<String> deployFromUrl(@RequestBody Map<String, String> requestBody) {
		String fileUrl = requestBody.get("fileUrl");
		try (InputStream inputStream = new URL(fileUrl).openStream()) {
			String filename = Paths.get(new URI(fileUrl).getPath()).getFileName().toString();

			// Deploy the process definition
			var deploymentResult = client.newDeployResourceCommand()
					.addResourceStream(inputStream, filename)
					.send()
					.join();

			return ResponseEntity.ok("✅ Deployed successfully: " + filename +
					" (Process Key: " + deploymentResult.getProcesses().get(0).getProcessDefinitionKey() + ")");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("❌ Failed to deploy from URL: " + e.getMessage());
		}
	}

	@PostMapping("/start")
	public ResponseEntity<String> startProcessInstance(@RequestBody Map<String, Object> requestBody) {
		try {
			String processId = (String) requestBody.get("processId");
			Map<String, Object> variables = (Map<String, Object>) requestBody.getOrDefault("variables", new HashMap<>());

			System.out.println("Starting process instance for: " + processId);

			// Create process instance with variables
			var processInstanceResult = client.newCreateInstanceCommand()
					.bpmnProcessId(processId)
					.latestVersion()
					.variables(variables)
					.send()
					.join();

			return ResponseEntity.ok("✅ Process instance started: " + processId +
					" (Instance Key: " + processInstanceResult.getProcessInstanceKey() + ")");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("❌ Failed to start process: " + e.getMessage());
		}
	}

	@GetMapping("/start/{processId}")
	public ResponseEntity<String> startProcessInstanceById(@PathVariable String processId) {
		try {
			System.out.println("Starting process instance for: " + processId);

			var processInstanceResult = client.newCreateInstanceCommand()
					.bpmnProcessId(processId)
					.latestVersion()
					.send()
					.join();

			return ResponseEntity.ok("✅ Process instance started: " + processId +
					" (Instance Key: " + processInstanceResult.getProcessInstanceKey() + ")");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("❌ Failed to start process: " + e.getMessage());
		}
	}
}