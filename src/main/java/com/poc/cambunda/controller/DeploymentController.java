package com.poc.cambunda.controller;

import com.poc.cambunda.service.DeploymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/process")
public class DeploymentController {

	private final DeploymentService deploymentService;

	public DeploymentController(DeploymentService deploymentService) {
		this.deploymentService = deploymentService;
	}

	@PostMapping("/deploy")
	public ResponseEntity<String> deployFromClasspath(@RequestBody Map<String, String> requestBody) {
		return ResponseEntity.ok(deploymentService.deployFromClasspath(requestBody.get("connectorName")));
	}

	@PostMapping("/deploy/url")
	public ResponseEntity<String> deployFromUrl(@RequestBody Map<String, String> requestBody) {
		return ResponseEntity.ok(deploymentService.deployFromUrl(requestBody.get("fileUrl")));
	}

	@PostMapping("/start")
	public ResponseEntity<String> startProcessInstance(@RequestBody Map<String, Object> requestBody) {
		String processId = (String) requestBody.get("processId");
		Map<String, Object> variables = (Map<String, Object>) requestBody.getOrDefault("variables", new HashMap<>());
		return ResponseEntity.ok(deploymentService.startProcess(processId, variables));
	}

	@GetMapping("/start/{processId}")
	public ResponseEntity<String> startProcessInstanceById(@PathVariable String processId) {
		return ResponseEntity.ok(deploymentService.startProcess(processId));
	}
}
