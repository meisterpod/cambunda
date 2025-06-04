package com.poc.cambunda.dto.taskDTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskSearchRequest {

    private Filter filter = new Filter();
    private List<Sort> sort =  new ArrayList<Sort>();
    private int pageSize;

    @Data
    public static class Filter {
        private String assignee;
        private String state;
    }

    @Data
    public static class Sort {
        private String field;
        private String order;
    }
}
