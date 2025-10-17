package com.vickllny.config;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public RedisChatMemoryStore redisChatMemoryStore(){
        return new RedisChatMemoryStore();
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
        return id -> MessageWindowChatMemory.builder()
                .chatMemoryStore(redisChatMemoryStore())
                .id(id)
                .maxMessages(20)
                .build();
    }


}
