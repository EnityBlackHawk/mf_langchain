package org.mf.langchain.controller;

import org.mf.langchain.auto.Airline;
import org.mf.langchain.connections.MongoConnection;
import org.mf.langchain.connections.MongoTemplateRegistry;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/runtime-connection")
public class MongoController {

    private final MongoTemplateRegistry _registry;

    public MongoController(MongoTemplateRegistry registry) {
        _registry = registry;
    }

    @PostMapping("/add")
    public String addConnection(@RequestBody MongoConnection connection) {
        _registry.addMongoTemplate(connection);
        return "add connection";
    }

    @PostMapping("/{id}/insert-doc")
    public String insertDocument(@PathVariable String id, @RequestBody Airline airline) {
        var template = _registry.getTemplate(id);
        if (template == null) {
            return "connection not found";
        }
        template.insert(airline);
        return "insert document";
    }
}
