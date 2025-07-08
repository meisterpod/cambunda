package com.poc.langchain.springai.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiConfig {

    @Value("${spring.ai.mistralai.chat.options.model}")
    private String apiModel;

    @Value("${spring.ai.mistralai.api-key}")
    private String apiKey;

    /*@Bean
    public MistralAiApi mistralAiApi() {
        return new MistralAiApi(apiKey);
    }

    @Bean
    public MistralAiChatOptions mistralAiChatOptions() {
        return MistralAiChatOptions.builder()
                .model(apiModel)
                .temperature(0.7)
                .maxTokens(200)
                .build();
    }

    @Bean
    public MistralAiChatModel mistralAiChatModel(MistralAiApi mistralAiApi, MistralAiChatOptions mistralAiChatOptions) {
        return MistralAiChatModel.builder()
                .mistralAiApi(mistralAiApi)
                .defaultOptions(mistralAiChatOptions)
                .build();
    }*/

    /*@Bean
    public VectorStore vectorStore(MistralAiApi.EmbeddingModel embeddingModel) {
        return new MemoryVectorStore(embeddingModel);
    }*/

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().build();
    }
}
