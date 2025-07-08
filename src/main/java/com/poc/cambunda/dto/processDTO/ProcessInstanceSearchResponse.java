package com.poc.cambunda.dto.processDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Response structure for a process instance search query in Camunda Operate.")
public class ProcessInstanceSearchResponse {

    @Schema(description = "List of process instances matching the search criteria.")
    private List<ProcessInstanceResponse> items;

    @Schema(description = "Total number of matching process instances across all pages.", example = "42")
    private long total;

    @Schema(description = "Values used for pagination with the 'searchAfter' parameter.")
    private List<String> sortValues;
}
