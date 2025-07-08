package com.poc.langchain.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class KnowledgeService {
    public String loadMarkdown(String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("policies/" + filename);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}