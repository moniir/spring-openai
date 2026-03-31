package com.example.spring_openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PrompthTemplateController {

    private final ChatClient chatClient;

    public PrompthTemplateController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

   /* String promptTemplate = """
            A customer named {customerName} sent the following message:
            {customerMessage}

            write a polite and helpful email response addressing the issue.
            Maintain a professional tone and provide reassurance.

            Respond as if you're writing the email body only. Don't include subject, signature
            """;*/

    //Recommended approach
    @Value("classpath:/promptTemplate/userPromptTemplate.st")
    Resource userPromptTemplate;

    @GetMapping("/email")
    public String chat(@RequestParam("customerName") String customerName, @RequestParam("customerMessage") String customerMessage){
        return chatClient
                .prompt()
                .system("""
                        You are a professional customer service assistant which helps drafting email 
                        response to improve the productivity of the customer support team 
                        """)
                .user(promptTemplateSpec ->
                       // promptTemplateSpec.text(promptTemplate)
                        promptTemplateSpec.text(userPromptTemplate)
                                .param("customerName",customerName)
                                .param("customerMessage",customerMessage))
                .call().content();

    }
    @Value("classpath:/promptTemplate/systemPromptTemplate.st")
    Resource systemPromptTemplate;

    @GetMapping("/prompt-stuffing")
    public String promptStuffing(@RequestParam("message") String message){
        return chatClient
                .prompt()
                .system(systemPromptTemplate)
                .user(message)
                .call().content();

    }
}
