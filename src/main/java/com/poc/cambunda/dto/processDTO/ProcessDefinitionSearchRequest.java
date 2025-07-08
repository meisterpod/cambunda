package com.poc.cambunda.dto.processDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Search request for retrieving process definitions based on filters, size, and sorting.")
public class ProcessDefinitionSearchRequest {

    @Schema(description = "Filter criteria to narrow down process definition results. Leave null if not specified.")
    private ProcessDefinitionFilter filter;

    @Schema(description = "Maximum number of results to return. Default is 5.", example = "10", defaultValue = "5")
    private Integer size;

    @Schema(description = "Sorting criteria for the search results. Leave null if not specified.")
    private List<Sort> sort;

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Fields to filter process definitions.")
    public static class ProcessDefinitionFilter {

        @Schema(description = "Name of the process definition. Leave null if not specified.", example = "Invoice Processing")
        private String name;

        @Schema(description = "BPMN process ID used in the BPMN model. Leave null if not specified.", example = "Process_Invoice_001")
        private String bpmnProcessId;

        @Schema(description = "Specific version of the process definition. Leave null if not specified.", example = "2")
        private Integer version;

        @Schema(description = "Name of the BPMN resource file deployed. Leave null if not specified.", example = "invoice-process.bpmn")
        private String resourceName;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Sort configuration to order the returned process definitions.")
    public static class Sort {

        @Schema(description = "Field to sort by.", example = "version", allowableValues = {"bpmnProcessId", "version", "name"})
        private String field;

        @Schema(description = "Order of sorting: ASC for ascending, DESC for descending.", example = "DESC", allowableValues = {"ASC", "DESC"})
        private String order;
    }
}
