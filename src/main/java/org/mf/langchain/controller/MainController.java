package org.mf.langchain.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.mf.langchain.DTO.*;
import org.mf.langchain.exception.InvalidData;
import org.mf.langchain.metadata.DbMetadata;
import org.mf.langchain.service.LLMService;
import org.mf.langchain.util.TryCast;
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

    @PostMapping("/expr")
    public MfEntity<?> mainEndpoint(@RequestBody MfEntity<?> data) throws Throwable {
        return switch (data.nextStep()) {
            case INIT -> service.initFlow(TryCast.cast(data.data(), SpecificationDTO.class, () -> new InvalidData(data.nextStep())));
            case VERIFY_CARDINALITY -> null;
            case GENERATE_MODEL -> null;
            case GENERATE_JAVA_CODE -> null;
        };
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
    public List<RelationCardinality> getRelationsCardinality(@RequestBody Credentials cred) throws SQLException {
        var dbm = new DbMetadata(cred.connectionString(), cred.username(), cred.password(), null);
        return service.getRelationsCardinality(dbm);
    }

}
