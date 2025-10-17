package com.vickllny.service;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        streamingChatModel = "openAiStreamingChatModel"
)
public interface ConsultantService {

    /**
     * 用于聊天
     * @param message
     * @return
     */
    Flux<String> chat(String message);

}
