package com.poc.langchain.langchain4j.config;

import com.poc.langchain.langchain4j.agent.LangChainAgent;
import com.poc.langchain.langchain4j.tool.DeploymentTool;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangChainConfig {

    @Value("${quarkus.langchain4j.openai.chat-model.model-name}")
    private String apiModel;

    @Value("${quarkus.langchain4j.openai.api-key}")
    private String apiKey;

    @Bean
    public ChatModel chatModel() {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName(apiModel)
                .build();
    }

    @Bean
    public LangChainAgent supportAgent(ChatModel chatModel, DeploymentTool tool) {
        return AiServices.builder(LangChainAgent.class)
                .chatModel(chatModel)
                .tools(tool)
                .build();
    }
}