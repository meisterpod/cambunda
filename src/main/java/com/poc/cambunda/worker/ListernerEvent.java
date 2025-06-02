package com.poc.cambunda.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ListernerEvent {

    @Value("http://localhost:9090")
    private String connectorUrl;

    @Autowired
    private RestTemplate restTemplate;

    @JobWorker(type = "StartEvent")
    public void processEvent(JobClient client, ActivatedJob job) {
        Map<String, Object> variables = job.getVariablesAsMap();
        System.out.println("Notifying banking-ws with vars: " + variables);

        try {
            // Send HTTP notification to banking-ws
            String url = connectorUrl + "/api/tasks/notify";

            restTemplate.postForEntity(url, variables, Void.class);

            client.newCompleteCommand(job.getKey())
                    .send()
                    .join();
            System.out.println("Notification sent and job completed.");
        } catch (Exception e) {
            System.err.println("Error notifying banking-ws: " + e.getMessage());
            client.newFailCommand(job.getKey())
                    .retries(job.getRetries() - 1)
                    .errorMessage("Failed to notify banking-ws: " + e.getMessage())
                    .send()
                    .join();
        }
    }
}