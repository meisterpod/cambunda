package com.poc.cambunda.dto.formDTO;

import lombok.Data;

@Data
public class FormSearchRequest {
    private String formId;
    private String processDefinitionKey;
    private String version;
}
