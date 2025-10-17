package com.vickllny.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        streamingChatModel = "openAiStreamingChatModel",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface ConsultantService {

    /**
     * 用于聊天
     * @param message
     * @return
     */
    @SystemMessage(value = "你是林哥的助手柳岩，聪明又多金")
    Flux<String> chat(@MemoryId String memoryId, @UserMessage  String message);

}
