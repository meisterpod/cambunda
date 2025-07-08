package com.poc.cambunda.controller;

import com.poc.cambunda.dto.processDTO.*;
import com.poc.cambunda.service.ProcessService;
import com.poc.cambunda.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/processes")
public class ProcessController {

    private final ProcessService processService;

    private final TokenService tokenService;

    public ProcessController(ProcessService processService, TokenService tokenService) {
        this.processService = processService;
        this.tokenService = tokenService;
    }

    @GetMapping("/definitions/{id}")
    public ResponseEntity<ProcessDefinitionResponse> getProcessDefinitionById(@PathVariable String id) {
        return ResponseEntity.ok(processService.getProcessDefinitionById(id, tokenService.getToken()));
    }

    @PostMapping("/definitions/search")
    public ResponseEntity<ProcessDefinitionSearchResponse> searchProcessDefinitions(@RequestBody ProcessDefinitionSearchRequest request) {
        return ResponseEntity.ok(processService.searchProcessDefinitions(request, tokenService.getToken()));
    }

    @GetMapping("/instances/{id}")
    public ResponseEntity<ProcessInstanceResponse> getInstanceById(@PathVariable String id) {
        return ResponseEntity.ok(processService.getProcessInstanceById(id, tokenService.getToken()));
    }

    @PostMapping("/instances/search")
    public ResponseEntity<ProcessInstanceSearchResponse> searchProcessInstances(@RequestBody ProcessInstanceSearchRequest request) {
        return ResponseEntity.ok(processService.searchProcessInstances(request, tokenService.getToken()));
    }

    @DeleteMapping("/instances/{id}")
    public ResponseEntity<String> deleteInstanceById(@PathVariable String id) {
        return ResponseEntity.ok(processService.deleteProcessInstanceById(id, tokenService.getToken()));
    }
}