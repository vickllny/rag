package com.vickllny.config;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.List;


public class RedisChatMemoryStore implements ChatMemoryStore {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private String key(final Object memoryId){
        return "rag:session:" + memoryId;
    }

    @Override
    public List<ChatMessage> getMessages(final Object memoryId) {
        String key = key(memoryId);
        final String json = redisTemplate.opsForValue().get(key);
        return ChatMessageDeserializer.messagesFromJson(json);
    }

    @Override
    public void updateMessages(final Object memoryId, final List<ChatMessage> messages) {
        String key = key(memoryId);
        final String json = ChatMessageSerializer.messagesToJson(messages);
        redisTemplate.opsForValue().set(key, json, Duration.ofHours(24));
    }

    @Override
    public void deleteMessages(final Object memoryId) {
        String key = key(memoryId);
        redisTemplate.delete(key);
    }
}
