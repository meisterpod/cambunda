package com.poc.langchain.springai.agent;

import com.poc.langchain.service.KnowledgeService;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class SpringAgent {

    private final PromptTemplate template;
    private final KnowledgeService knowledgeService;

    public SpringAgent(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
        this.template = new PromptTemplate("""       
            {{question}}
        """);
    }

    @Retryable(
            include = {NonTransientAiException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public Prompt createPrompt(String userQuestion) throws IOException {
        String markdown = knowledgeService.loadMarkdown("process/generation_prompt.md");
        return template.create(Map.of(
                "question", userQuestion
        ));
    }
}
