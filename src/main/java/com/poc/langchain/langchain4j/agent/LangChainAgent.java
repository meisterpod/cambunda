package com.poc.langchain.langchain4j.agent;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.SystemMessage;


public class LangChainAgent {

    private final OpenAiChatModel chatModel;

    public LangChainAgent(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @SystemMessage("You are a helpful support agent for Cambunda. You assist users in deploying BPMN processes, " +
            "starting processes by ID, and validating tasks assigned to them.")
    public String chat(String prompt) {
        return chatModel.chat(prompt);
    }
}
