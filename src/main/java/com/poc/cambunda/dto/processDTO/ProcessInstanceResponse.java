package com.poc.cambunda.dto.processDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Detailed information about a specific process instance.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessInstanceResponse {

    @Schema(description = "Unique key of the process instance.", example = "2251799813685253")
    private String key;

    @Schema(description = "Key of the process definition associated with this instance.", example = "2251799813685230")
    private String processDefinitionKey;

    @Schema(description = "BPMN process ID of the instance.", example = "invoice-process")
    private String bpmnProcessId;

    @Schema(description = "Version number of the process definition.", example = "4")
    private long processVersion;

    @Schema(description = "Indicates whether this instance has an incident.", example = "false")
    private Boolean incident;

    @Schema(description = "Current state of the process instance (e.g., ACTIVE, COMPLETED, CANCELED).", example = "ACTIVE")
    private String state;

    @Schema(description = "Timestamp when the instance started.", example = "2025-06-20T13:50:11.365+0000")
    private String startDate;

    @Schema(description = "Timestamp when the instance ended (null if still active).", example = "2025-06-20T14:12:00.000+0000")
    private String endDate;

    @Schema(description = "Tenant ID for multi-tenancy support.", example = "<default>")
    private String tenantId;
}