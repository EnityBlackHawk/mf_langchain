package org.mf.langchain.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.mf.langchain.DTO.Credentials;
import org.mf.langchain.DTO.Relations;
import org.mf.langchain.DTO.SpecificationDTO;
import org.mf.langchain.metadata.DbMetadata;
import org.mf.langchain.DTO.RelationsCardinalityDTO;
import org.mf.langchain.service.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    private final LLMService service;

    MainController(@Autowired LLMService service) {
        this.service = service;
    }

    @PostMapping()
    public String Generate(@RequestBody SpecificationDTO spec, HttpServletResponse response) {
        var result = service.Generate(spec);
        response.addHeader("Total-Tokens", String.valueOf(result.tokens()));
        response.addHeader("Prompt", result.prompt());
        return result.aiResponse();
    }

    @PostMapping("/expr/rels")
    public List<Relations> getRelations(@RequestBody String text) {
        var obj = service.getRelations(text, null);
        return obj;
    }

    @PostMapping("/expr/relsCard")
    public List<RelationsCardinalityDTO> getRelationsCardinality(@RequestBody Credentials cred) throws SQLException {
        var dbm = new DbMetadata(cred.connectionString(), cred.username(), cred.password(), null);
        return service.getRelationsCardinality(dbm);
    }

}
