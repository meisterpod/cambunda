package com.poc.cambunda.service.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.Map;

@Component
public class TaskEvent {

    @Value("http://localhost:9090")
    private String connectorUrl;

    @Autowired
    private RestTemplate restTemplate;

    @JobWorker(type = "ServiceTask")
    public void processTask(final JobClient client, final ActivatedJob job) {
        /* Get job variables if needed
           Map<String, Object> variables = job.getVariablesAsMap();
           System.out.println("Job variables: " + variables);
           String targetApp = (String) job.getVariablesAsMap().get("targetApp");
           Here base on the targeted app will find the needed information in our database.
        */
        try {
            String taskName = (String) job.getVariablesAsMap().get("taskName");
            System.out.println("Notify about one service task to be processed " + taskName);
            String url = connectorUrl + "/api/tasks/notify";
            restTemplate.postForEntity(url, Map.of("taskName", taskName), Void.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Task not found, the tasks will be aborted");
        } finally {
            // Complete the job - this is crucial!
            client.newCompleteCommand(job.getKey())
                    .variables(Collections.emptyMap()) // or add new variables if needed
                    .send()
                    .join();
            System.out.println("Service task processed successfully");
        }
    }
}