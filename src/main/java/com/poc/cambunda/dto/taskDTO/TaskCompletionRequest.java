package com.poc.cambunda.dto.taskDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Schema(description = "Request payload for completing a task")
public class TaskCompletionRequest {

    @NotNull
    @Schema(description = "ID of the task to complete", example = "2251799814758497")
    private String taskId;

    @NotNull
    @Schema(description = "Variables to submit with the completion, based on form fields",
            example = "{\"approvalDecision\": \"approve\"}")
    private Map<String, Object> variables;
}
