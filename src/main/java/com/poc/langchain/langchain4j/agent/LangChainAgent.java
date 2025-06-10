package com.poc.langchain.langchain4j.agent;

import dev.langchain4j.service.SystemMessage;


public interface LangChainAgent {
    @SystemMessage("You are a helpful support agent for Cambunda. You assist users in deploying BPMN processes, " +
            "starting processes by ID, and validating tasks assigned to them.")
    String chat(String prompt);
}