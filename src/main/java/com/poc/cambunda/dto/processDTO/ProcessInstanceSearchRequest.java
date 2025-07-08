package com.poc.cambunda.dto.processDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.checkerframework.checker.index.qual.Positive;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Search request for retrieving process instances based on filters, size, and sorting.")
public class ProcessInstanceSearchRequest {

    @Schema(description = "Filter criteria to narrow down process instance results. Leave null if not specified.")
    private ProcessInstanceFilter filter;

    @Positive
    @Schema(description = "Maximum number of results to return.", defaultValue = "5", example = "20", minimum = "1")
    private Integer size;

    @Schema(description = "Sorting preferences for the result list. Leave null if not specified.")
    private List<Sort> sort;

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Defines filtering options for process instance search.")
    public static class ProcessInstanceFilter {

        @Positive
        @Schema(description = "Unique key of the process instance. Leave null if not specified.", example = "123456")
        private Long key;

        @Positive
        @Schema(description = "Version of the process definition. Leave null if not specified.", example = "2")
        private Integer processVersion;

        @Schema(description = "Version tag of the process definition. Leave null if not specified.", example = "release-v2")
        private String processVersionTag;

        @Schema(description = "BPMN process ID of the instance. Leave null if not specified.", example = "invoice-process")
        private String bpmnProcessId;

        @Positive
        @Schema(description = "Key of the parent process instance. Leave null if not specified.", example = "987654")
        private Long parentKey;

        @Positive
        @Schema(description = "Key of the parent flow node instance. Leave null if not specified.", example = "456789")
        private Long parentFlowNodeInstanceKey;

        @Schema(description = "Start date for filtering instances (ISO-8601 format). Leave null if not specified.", example = "2025-06-01T00:00:00Z")
        private String startDate;

        @Schema(description = "End date for filtering instances (ISO-8601 format). Leave null if not specified.", example = "2025-06-30T23:59:59Z")
        private String endDate;

        @Schema(description = "State of the process instance. Leave null if not specified.", example = "ACTIVE", allowableValues = {"ACTIVE", "COMPLETED", "CANCELED"})
        private String state;

        @Schema(description = "Whether the instance has incidents. Leave null if not specified.", example = "false")
        private Boolean incident;

        @Positive
        @Schema(description = "Key of the process definition. Leave null if not specified.", example = "22334455")
        private Long processDefinitionKey;

        @Schema(description = "Tenant ID used for multi-tenancy. Leave null if not specified.", example = "<default>")
        private String tenantId;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Sorting instructions for ordering search results.")
    public static class Sort {

        @Schema(description = "Field name to sort by.", example = "startDate", allowableValues = {"startDate", "endDate", "state"})
        private String field;

        @Schema(description = "Sort order (ASC or DESC).", example = "DESC", allowableValues = {"ASC", "DESC"})
        private String order;
    }
}
