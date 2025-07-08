package com.poc.cambunda.dto.processDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Response wrapper for search results of process definitions.")
public class ProcessDefinitionSearchResponse {

    @Schema(description = "List of matched process definitions.")
    private List<ProcessDefinitionResponse> items;

    @Schema(description = "Total number of matched items.", example = "5")
    private long total;

    @Schema(description = "Sort values used for pagination.", example = "[\"2251799813733403\"]")
    private List<String> sortValues;
}
