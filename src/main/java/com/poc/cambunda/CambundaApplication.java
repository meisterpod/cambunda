package com.poc.cambunda;

import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class CambundaApplication {

	@Autowired
	private ZeebeClient zeebeClient;

	public static void main(String[] args) {
		SpringApplication.run(CambundaApplication.class, args);
	}
}
