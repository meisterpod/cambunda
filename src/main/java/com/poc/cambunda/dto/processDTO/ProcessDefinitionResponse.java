package com.poc.cambunda.dto.processDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Represents a deployed process definition in Camunda Operate.")
public class ProcessDefinitionResponse {

    @Schema(description = "Unique identifier of the process definition.", example = "2251799813733403")
    private String key;

    @Schema(description = "Name of the process definition as defined in the BPMN file.", example = "Order Processing")
    private String name;

    @Schema(description = "BPMN process ID used to reference the process model.", example = "Process_Order_123")
    private String bpmnProcessId;

    @Schema(description = "Version number of the deployed process.", example = "3")
    private Integer version;

    @Schema(description = "Name of the BPMN resource file used during deployment.", example = "order-process.bpmn")
    private String resourceName;

    @Schema(description = "Tenant identifier, if multi-tenancy is enabled.", example = "<default>")
    private String tenantId;
}
