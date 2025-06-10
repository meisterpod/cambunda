package com.poc.langchain.springai.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring-ai")
public class SpringAiController {

    private final OpenAiChatModel chatModel;

    @Autowired
    public SpringAiController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> ask(@RequestBody String prompt) {
        return ResponseEntity.ok(chatModel.call(prompt));
    }
}