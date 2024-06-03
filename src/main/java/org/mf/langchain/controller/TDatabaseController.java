package org.mf.langchain.controller;

import org.mf.langchain.DTO.CreateDatabaseDTO;
import org.mf.langchain.DTO.ResponseCreateDatabaseDTO;
import org.mf.langchain.service.TDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/temp")
public class TDatabaseController {

    private final TDatabaseService tDatabaseService;

    public TDatabaseController(@Autowired() TDatabaseService tDatabaseService) {
        this.tDatabaseService = tDatabaseService;
    }

    @PostMapping("/create")
    public ResponseCreateDatabaseDTO createDatabase(@RequestBody CreateDatabaseDTO databaseDTO) {
        return tDatabaseService.createDatabaseAndExecuteSQL(databaseDTO.databaseName(), databaseDTO.sql());
    }

    @PostMapping("/{databaseName}")
    public String executeSQL(@RequestBody String sql, @PathVariable String databaseName) {
        return tDatabaseService.executeSQL(databaseName, sql);
    }

    @GetMapping("/{databaseName}")
    public String getSQL(@PathVariable String databaseName) {
        return tDatabaseService.getCreateSQL(databaseName);
    }

}
