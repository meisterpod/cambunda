package com.poc.langchain.springai.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiConfig {

    @Value("${spring.ai.openai.chat.options.model}")
    private String apiModel;

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Bean
    public OpenAiApi openAiApi() {
        return OpenAiApi.builder()
                .apiKey(apiKey)
                .build();
    }

    @Bean
    public OpenAiChatOptions openAiChatOptions() {
        return OpenAiChatOptions.builder()
                .model(apiModel)
                .temperature(0.4)
                .maxTokens(200)
                .build();
    }

    @Bean
    public OpenAiChatModel openAiChatModel(OpenAiApi openAiApi, OpenAiChatOptions openAiChatOptions) {
        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(openAiChatOptions)
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().build();
    }
}