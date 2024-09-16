package org.mf.langchain.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.mf.langchain.DTO.*;
import org.mf.langchain.exception.InvalidData;
import org.mf.langchain.metadata.DbMetadata;
import org.mf.langchain.service.LLMService;
import org.mf.langchain.util.TryCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;


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
            case GENERATE_MODEL -> service.generateModel(TryCast.cast(data.data(), GenerateSpecsDTO.class, () -> new InvalidData(data.nextStep())));
            case GENERATE_JAVA_CODE -> service.generateJavaCode(TryCast.cast(data.data(), ModelDTO.class, () -> new InvalidData(data.nextStep())));
            case FINAL -> null;
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
        var dbm = new DbMetadata(cred.getConnectionString(), cred.getUsername(), cred.getPassword(), null);
        return service.getRelationsCardinality(dbm);
    }

    @PostMapping("/expr/compile/{className}")
    public String compile(@PathVariable String className, @RequestBody String text) throws Exception {
        return service.compileAndRun(Map.of(className, text));
    }

    @PostMapping("/art")
    public String runBasic(@RequestBody Credentials credentials, HttpServletResponse response) throws Exception {
        var result = service.runBasic(credentials);
        response.addHeader("Total-Tokens", String.valueOf(result.tokens()));
        response.addHeader("Prompt", result.prompt());
        return result.aiResponse();
    }

    @PostMapping("/art/rerun-last")
    public String runRerun(HttpServletResponse response) throws SQLException {
        var result = service.reRunBasic();
        response.addHeader("Total-Tokens", String.valueOf(result.tokens()));
        response.addHeader("Prompt", result.prompt());
        return result.aiResponse();
    }

    @PostMapping("/art/push")
    public String runPush() throws SQLException {
        service.pushMigration();
        return "OK";
    }

}
