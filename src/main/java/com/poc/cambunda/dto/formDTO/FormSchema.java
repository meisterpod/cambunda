package com.poc.cambunda.dto.formDTO;

import lombok.Data;

import java.util.List;

@Data
public class FormSchema {
    private String id;
    private String executionPlatform;
    private String executionPlatformVersion;
    private Exporter exporter;
    private int schemaVersion;
    private List<FormComponent> components;
    private String type;
}
