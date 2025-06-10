package com.poc.langchain.langchain4j.websocket;

import com.poc.langchain.langchain4j.agent.LangChainAgent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class AgentWebSocket {

    private final LangChainAgent langChainAgent;

    public AgentWebSocket(LangChainAgent langChainAgent) {
        this.langChainAgent = langChainAgent;
    }

    @MessageMapping("/prompt")
    @SendTo("/topic/reply")
    public String handleMessage(String prompt) {
        return langChainAgent.chat(prompt);
    }
}