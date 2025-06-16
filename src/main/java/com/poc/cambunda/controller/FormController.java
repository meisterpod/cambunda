package com.poc.cambunda.controller;

import com.poc.cambunda.dto.formDTO.FormSearchRequest;
import com.poc.cambunda.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class FormController {

    @Autowired
    private FormService formService;

    @GetMapping("/{formId}")
    public ResponseEntity<Mono<ResponseEntity<String>>> getForm(@PathVariable String formId,
                                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                                                @RequestParam String processDefinitionKey,
                                                                @RequestParam String version) {
        FormSearchRequest formSearchRequest = new FormSearchRequest();
        formSearchRequest.setFormId(formId);
        formSearchRequest.setProcessDefinitionKey(processDefinitionKey);
        formSearchRequest.setVersion(version);
        return ResponseEntity.ok(formService.getForm(formSearchRequest, authHeader));
    }
}
