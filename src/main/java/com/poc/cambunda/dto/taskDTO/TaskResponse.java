package com.poc.cambunda.dto.taskDTO;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class TaskResponse {
    private String id;
    private String name;
    private String taskDefinitionId;
    private String processName;
    private ZonedDateTime creationDate;
    private ZonedDateTime completionDate;
    private String assignee;
    private String taskState;
    private List<String> sortValues;
    private boolean isFirst;
    private String formKey;
    private String formId;
    private String formVersion;
    private boolean isFormEmbedded;
    private String processDefinitionKey;
    private String processInstanceKey;
    private String tenantId;
    private ZonedDateTime dueDate;
    private ZonedDateTime followUpDate;
    private List<String> candidateGroups;
    private List<String> candidateUsers;
    private Object variables;
    private Object context;
    private String implementation;
    private int priority;
}
