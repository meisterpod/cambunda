package com.poc.cambunda.dto.formDTO;

import ch.qos.logback.core.Layout;
import lombok.Data;
import java.util.List;

@Data
public class FormComponent {
    private String label;
    private List<FormValue> values;
    private String key;
    private String type;
    private String id;
    private Layout layout;
}