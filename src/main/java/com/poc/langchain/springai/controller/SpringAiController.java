package com.poc.langchain.springai.controller;

import com.poc.langchain.springai.agent.SpringAgent;
import com.poc.langchain.springai.tool.DeployProcessTool;
import com.poc.langchain.springai.tool.ProcessTool;
import com.poc.langchain.springai.tool.TaskProcessTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/spring-ai")
public class SpringAiController {

    private final ChatClient chatClient;
    private final SpringAgent springAgent;

    public SpringAiController(MistralAiChatModel chatModel,
                              ChatMemory chatMemory,
                              DeployProcessTool deployTool,
                              TaskProcessTool taskTool,
                              ProcessTool processTool,
                              SpringAgent springAgent) {
        this.chatClient = ChatClient.builder(chatModel)
                .defaultTools(deployTool, taskTool, processTool)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
        this.springAgent = springAgent;
    }


    @PostMapping(path = "/chat", consumes = "text/plain", produces = "text/plain")
    public String chat(@RequestBody String message) throws IOException {
        Prompt prompt = springAgent.createPrompt(message);
        return chatClient.prompt(message).call().content();
    }
}