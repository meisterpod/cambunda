package com.poc.langchain.springai.agent;

import com.poc.langchain.springai.tool.DeployProcessTool;
import com.poc.langchain.springai.tool.ProcessTool;
import com.poc.langchain.springai.tool.TaskProcessTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Component;

@Component
public class FallbackChatClient {

    private final ChatClient mistralClient;
    private final ChatClient openAiClient;

    public FallbackChatClient(MistralAiChatModel mistralModel,
                              OpenAiChatModel openAiModel,
                              ChatMemory chatMemory,
                              DeployProcessTool deployTool,
                              TaskProcessTool taskTool,
                              ProcessTool processTool) {

        this.mistralClient = ChatClient.builder(mistralModel)
                .defaultTools(deployTool, taskTool, processTool)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();

        this.openAiClient = ChatClient.builder(openAiModel)
                .defaultTools(deployTool, taskTool, processTool)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public String call(String message) {
        try {
            return mistralClient.prompt(message).call().content();
        } catch (Exception e) {
            System.err.println("⚠️ Mistral failed, falling back to OpenAI: " + e.getMessage());
            return openAiClient.prompt(message).call().content();
        }
    }
}
