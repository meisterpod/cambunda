package com.poc.cambunda.dto.formDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "Schema representing a form definition returned by the Camunda Tasklist API")
public class FormSchema {

    @NotNull
    @Schema(description = "Unique identifier of the form", example = "approve-form")
    private String id;

    @Schema(description = "Execution platform name", example = "Camunda Platform")
    private String executionPlatform;

    @Schema(description = "Version of the execution platform", example = "8.1.0")
    private String executionPlatformVersion;

    @NotNull
    @Schema(description = "Information about the exporter tool that created this form schema")
    private Exporter exporter;

    @Schema(description = "Version of the form schema", example = "1")
    private int schemaVersion;

    @Schema(description = "List of components (fields) that make up the form")
    private List<FormComponent> components;

    @Schema(description = "Type of the form", example = "form")
    private String type;

    @Data
    @Schema(description = "Details about the exporter tool")
    public static class Exporter {

        @Schema(description = "Name of the exporter", example = "Camunda Modeler")
        private String name;

        @Schema(description = "Version of the exporter tool", example = "5.8.1")
        private String version;
    }

    @Data
    @Schema(description = "Layout information for form components")
    public static class Layout {

        @Schema(description = "Row position of the component in the layout", example = "1")
        private String row;
    }

    @Data
    @Schema(description = "Possible values for a form component, e.g., dropdown options")
    public static class FormValue {

        @Schema(description = "Label of the value option", example = "Approve")
        private String label;

        @Schema(description = "Actual value associated with the label", example = "approve")
        private String value;
    }

    @Data
    @Schema(description = "Represents a single form component, like a field or control")
    public static class FormComponent {

        @Schema(description = "Label shown to users for this component", example = "Approval Decision")
        private String label;

        @Schema(description = "List of selectable values (if applicable)")
        private List<FormValue> values;

        @Schema(description = "Key used as variable name to bind form input", example = "approvalDecision")
        private String key;

        @Schema(description = "Type of the form component, e.g. text, select, checkbox", example = "select")
        private String type;

        @Schema(description = "Unique identifier of the form component", example = "component-1234")
        private String id;

        @Schema(description = "Layout details for positioning the component")
        private Layout layout;
    }
}
