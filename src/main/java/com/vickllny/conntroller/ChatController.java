package com.vickllny.conntroller;

import com.vickllny.service.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    @Autowired
    private ConsultantService consultantService;

    @GetMapping(value = "/chat", produces = MediaType.TEXT_HTML_VALUE)
    public Flux<String> chat(String message){
        return consultantService.chat(message);
    }


}
