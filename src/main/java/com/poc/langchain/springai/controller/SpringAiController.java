package com.poc.langchain.springai.controller;

import com.poc.langchain.springai.agent.FallbackChatClient;
import com.poc.langchain.springai.agent.SpringAgent;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/spring-ai")
public class SpringAiController {

    private final FallbackChatClient fallbackChatClient;
    private final SpringAgent springAgent;

    public SpringAiController(FallbackChatClient fallbackChatClient, SpringAgent springAgent) {
        this.fallbackChatClient = fallbackChatClient;
        this.springAgent = springAgent;
    }


    @PostMapping(path = "/chat", consumes = "text/plain", produces = "text/plain")
    public String chat(@RequestBody String message) throws IOException {
        Prompt prompt = springAgent.createPrompt(message);
        return fallbackChatClient.call(message);
    }
}