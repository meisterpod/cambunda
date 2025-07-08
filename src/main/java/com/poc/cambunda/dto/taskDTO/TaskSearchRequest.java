package com.poc.cambunda.dto.taskDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Request object for searching tasks in Tasklist")
public class TaskSearchRequest {

    @Schema(description = "Filter criteria for the task search")
    private Filter filter = new Filter();

    @Schema(description = "Sorting preferences")
    private List<Sort> sort = new ArrayList<>();

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size must not exceed 100")
    @Schema(description = "Number of tasks to return", example = "10", defaultValue = "5")
    private int pageSize = 5;

    @Data
    @Schema(description = "Filters for task search")
    public static class Filter {
        @Schema(description = "Exact assignee to filter by", example = "demo", nullable = true)
        private String assignee;

        @Schema(description = "Task state: CREATED, COMPLETED, etc.", example = "CREATED", nullable = true)
        private String taskState;

        @Schema(description = "Whether the task must be assigned", example = "true", nullable = true)
        private Boolean assigned;

        @Schema(description = "Task definition ID", example = "Activity_123abc", nullable = true)
        private String taskDefinitionId;
    }

    @Data
    @Schema(description = "Sorting option")
    public static class Sort {
        @Schema(description = "Field to sort by (e.g. creationDate)", example = "creationDate", nullable = true)
        private String field;

        @Schema(description = "Sort order (ASC or DESC)", example = "DESC", nullable = true)
        private String order;
    }
}

