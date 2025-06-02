package com.poc.cambunda.worker;

import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collections;
import java.util.Map;

@Component
public class TaskEvent {

    @Value("http://localhost:9090")
    private String connectorUrl;

    @Autowired
    private RestTemplate restTemplate;

    @JobWorker(type = "NotifyUser")
    public void handleEndTask(final JobClient client, final ActivatedJob job) {
        /* Get job variables if needed
           Map<String, Object> variables = job.getVariablesAsMap();
           System.out.println("Job variables: " + variables);
           String targetApp = (String) job.getVariablesAsMap().get("targetApp");
           Here base on the targeted app will find the needed information in our database.
        */
        System.out.println("Notify the user about an assignee");
        try {
            String taskName = (String) job.getVariablesAsMap().get("taskName");
            String url = connectorUrl + "/tasks/deploy";
            restTemplate.postForEntity(connectorUrl + "/api/notify", Map.of("taskName", taskName), Void.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Contract not found, the tasks will be aborted");
        } finally {
            // Complete the job - this is crucial!
            client.newCompleteCommand(job.getKey())
                    .variables(Collections.emptyMap()) // or add new variables if needed
                    .send()
                    .join();

            System.out.println("NotifyUser job completed successfully");
        }
    }
}