package com.poc.cambunda.dto.formDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request payload for searching forms with optional filters")
public class FormSearchRequest {

    @Schema(description = "Unique identifier of the form", example = "approve-form")
    private String formId;

    @Schema(description = "Key of the process definition associated with the form", example = "invoice-approval")
    private String processDefinitionKey;

    @Schema(description = "Version of the form or process definition", example = "1")
    private String version;
}