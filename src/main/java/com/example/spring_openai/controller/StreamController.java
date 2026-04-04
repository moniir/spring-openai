package com.example.spring_openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequestMapping("/api")
@RestController
public class StreamController {

    private final ChatClient chatClient;

    @Value("classpath:/promptTemplate/systemPromptTemplate.st")
    Resource systemPromptTemplate;

    public StreamController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/stream")
    public Flux<String> chat(@RequestParam("message") String message) {
        return chatClient.prompt()
                .system(systemPromptTemplate)
                .user(message)
                .stream().content();
    }

}
