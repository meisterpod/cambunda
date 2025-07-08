package com.poc.cambunda.controller;

import com.poc.cambunda.dto.formDTO.FormResponse;
import com.poc.cambunda.dto.formDTO.FormSearchRequest;
import com.poc.cambunda.service.FormService;
import com.poc.cambunda.service.TokenService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/forms")
public class FormController {

    private final FormService formService;

    private final TokenService tokenService;

    public FormController(FormService formService, TokenService tokenService) {
        this.formService = formService;
        this.tokenService = tokenService;
    }

    @GetMapping("/{formId}")
    public Mono<FormResponse> getForm(@PathVariable String formId,
                                      @RequestParam String processDefinitionKey,
                                      @RequestParam(required = false) String version) {
        FormSearchRequest formSearchRequest = new FormSearchRequest();
        formSearchRequest.setFormId(formId);
        formSearchRequest.setProcessDefinitionKey(processDefinitionKey);
        formSearchRequest.setVersion(version);
        return formService.getForm(formSearchRequest, tokenService.getToken());
    }
}