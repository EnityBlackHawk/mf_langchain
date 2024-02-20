package org.mf.langchain.controller;

import org.mf.langchain.DTO.PromptDTO;
import org.mf.langchain.service.LangChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import retrofit2.http.POST;

import java.io.IOException;

@RestController
@RequestMapping("/api/query")
public class LMController {

    private final LangChainService service;

    LMController(LangChainService service) {
        this.service = service;
    }

    @PostMapping()
    public String generate(@RequestBody PromptDTO prompt) {
        return service.Generate(prompt.getPrompt());
    }

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestBody PromptDTO prompt) {
        SseEmitter emitter = new SseEmitter();
        service.GenerateStream(prompt.getPrompt(), (s) -> {
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    .data(s);
            try {
                emitter.send(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, emitter::completeWithError, (m) -> emitter.complete());


        return emitter;
    }

}
