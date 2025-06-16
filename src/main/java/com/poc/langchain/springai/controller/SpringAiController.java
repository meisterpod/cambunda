package com.poc.langchain.springai.controller;

import com.poc.langchain.springai.tool.TaskValidationTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring-ai")
public class SpringAiController {

    private final ChatClient chatClient;

    public SpringAiController(MistralAiChatModel chatModel, ChatMemory chatMemory, TaskValidationTool tools) {
        this.chatClient = ChatClient.builder(chatModel).defaultTools(tools)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
    }

    @PostMapping(path = "/chat", consumes = "text/plain", produces = "text/plain")
    public String chat(@RequestBody String message) {
        return chatClient.prompt().user(message).call().content();
    }
}