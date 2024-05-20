package org.mf.langchain.controller;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.output.Response;
import org.mf.langchain.DTO.MfResponse;
import org.mf.langchain.DTO.SpecificationDTO;
import org.mf.langchain.service.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {

    private final LLMService service;

    MainController(@Autowired LLMService service) {
        this.service = service;
    }

    @PostMapping()
    public MfResponse Generate(@RequestBody SpecificationDTO spec) {
        return service.Generate(spec);
    }

}
