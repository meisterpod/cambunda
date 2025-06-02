package com.poc.cambunda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import io.camunda.zeebe.client.ZeebeClient;
import java.util.Map;


@RestController
@RequestMapping("/")
public class TaskManagement {

    @Autowired
    private ZeebeClient client;

    // We use this when a user completes a form in banking-ws, and we want to inform that a process can
    // move forward.
    @PostMapping("/message")
    public ResponseEntity<Void> publishMessage(@RequestBody Map<String, Object> payload) {
        client.newPublishMessageCommand()
                .messageName((String) payload.get("messageName"))
                .correlationKey((String) payload.get("correlationKey"))
                .variables(payload)
                .send()
                .join();
        return ResponseEntity.ok().build();
    }
}
