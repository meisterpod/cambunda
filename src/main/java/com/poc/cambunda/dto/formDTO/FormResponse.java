package com.poc.cambunda.dto.formDTO;

import lombok.Data;

@Data
public class FormResponse {
    private String id;
    private String processDefinitionKey;
    private String schema; // This is a JSON string
    private int version;
    private String tenantId;
    private boolean isDeleted;
}
