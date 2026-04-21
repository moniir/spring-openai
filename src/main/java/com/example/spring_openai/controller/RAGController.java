package com.example.spring_openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/rag")
public class RAGController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:promptTemplate/systemPromptRandomDataTemplate.st")
    Resource promptTemplate;

    @Value("classpath:promptTemplate/systemPromptTemplateForPDF.st")
    Resource hrSystemTemplate;

    public RAGController(@Qualifier("chatMemoryChatClient") ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

   /* @GetMapping("/random/chat")
    public ResponseEntity<String> randomChat(@RequestHeader("username") String username,
                                             @RequestParam("message") String message) {

        SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3).similarityThreshold(0.5).build(); //part of spring ai for user search query to llm. converted into embedded vector
        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest); //Compare with stored document embeddings. apply topK & similarity threshold
        String similarCOntext = similarDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator())); // extract text based on probability and Joins them into one big string.
        String answer = chatClient.prompt().system(promptSystemSpec -> promptSystemSpec.text(promptTemplate).param("documents", similarCOntext))
                .advisors(a -> a.param(CONVERSATION_ID, username))
                .user(message).call().content();
        return ResponseEntity.ok(answer);
    }*/

    @GetMapping("/hrpolicy/chat")
    public ResponseEntity<String> hrPolicyChat(@RequestHeader("username") String username, @RequestParam("message") String message) {
        SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3)
                .similarityThreshold(0.6).build();
        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
        String similarContext = similarDocs.stream().map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));
        String answer = chatClient.prompt()
                .system(promptSystemSpec -> promptSystemSpec.text(hrSystemTemplate).param("documents", similarContext))
                .advisors(a -> a.param(CONVERSATION_ID, username))
                .user(message).call().content();
        return ResponseEntity.ok(answer);
    }
}
