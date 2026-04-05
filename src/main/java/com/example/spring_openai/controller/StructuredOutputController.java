package com.example.spring_openai.controller;

import com.example.spring_openai.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StructuredOutputController {

    private final ChatClient chatClient;

    public StructuredOutputController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("/chat-bean")
    public ResponseEntity<CountryCities> chatBean(@RequestParam("question") String question) {
        CountryCities countryCities = chatClient.prompt()
                .user(question)
                .call().entity(CountryCities.class);
        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/chat-bean-output")
    public ResponseEntity<CountryCities> chatBeanOutput(@RequestParam("question") String question) {
        CountryCities countryCities = chatClient.prompt()
                .user(question)
                .call().entity(new BeanOutputConverter<>(CountryCities.class));
        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/chat-list")
    public ResponseEntity<List<String>> chatList(@RequestParam("question") String question) {
        List<String> countryCities = chatClient.prompt()
                .user(question)
                .call().entity(new ListOutputConverter());
        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/chat-bean-list")
    public ResponseEntity<List<CountryCities>> chatBeanList(@RequestParam("question") String question) {
        List<CountryCities> countryCities = chatClient.prompt()
                .user(question)
                .call().entity(new ParameterizedTypeReference<List<CountryCities>>() {
                });
        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/chat-map")
    public ResponseEntity<Map<String,Object>> chatMap(@RequestParam("question") String question) {
        Map<String,Object> countryCities = chatClient.prompt()
                .user(question)
                .call().entity(new MapOutputConverter());
        return ResponseEntity.ok(countryCities);
    }

}
