package com.example.spring_openai.config;

import com.example.spring_openai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder){
       ChatOptions chatOptions = ChatOptions.builder().model("gpt-4.1-mini").maxTokens(100)
                .temperature(0.8).build();

        return chatClientBuilder
                .defaultOptions(chatOptions)
               // .defaultAdvisors(new TokenUsageAuditAdvisor())
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor()))
                .defaultSystem("""
                        You are an internal HR assistant. Your role is to help employees with question related to HR policies, such as\s
                        leave policies, working hours, benefits and code of conduct. If any user ask beyond these topics, kindly\s
                        inform them that you can only assist with queries related to HR policies.
                        """)
                .defaultUser("How can you help me ?")
                .build();
    }
}
