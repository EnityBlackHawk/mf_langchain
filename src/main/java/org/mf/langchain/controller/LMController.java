package org.mf.langchain.controller;

import org.mf.langchain.DTO.PromptDTO;
import org.mf.langchain.service.LangChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.POST;

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

}
