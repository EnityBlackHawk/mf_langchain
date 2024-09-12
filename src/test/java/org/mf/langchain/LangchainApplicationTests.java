package org.mf.langchain;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mf.langchain.metadata.DbMetadata;
import org.mf.langchain.service.CompilerService;
import org.mf.langchain.service.LLMService;
import org.mf.langchain.service.TDatabaseService;
import org.mf.langchain.util.QueryResult;
import org.mf.langchain.util.TemplatedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LangchainApplicationTests {

    private final CompilerService _compilerService;

    @Autowired
    LangchainApplicationTests(CompilerService compilerService) {
        _compilerService = compilerService;
    }


    @Test
    void writeAStory() {
        var ts = new TemplatedString("{{name}} is {{age}} years old");
        var result = ts.render(Pair.of("name", "John"), Pair.of("age", "25"));
        assertEquals("John is 25 years old", result);
    }

    @Test
    void compileModel() throws Exception {
        var result = _compilerService.compileAndRun("MfRuntimeClass", "public class MfRuntimeClass { private String name = \"Luan\"; public String hello() { return \"Hello \" + name; }}");
        assertEquals("Hello Luan", result);
    }

//    @Test
//    void getAllFromColumn() throws SQLException {
//
//        var mdb = new DbMetadata(tDatabaseService.createDatabase("test"), null);
//        DataImporter.Companion.runSQLFromFile("src/main/resources/data.sql", mdb.getConnection());
//        DataImporter.Companion.runSQLFromFile("inserts.sql", mdb.getConnection());
//
//        var query = "SELECT \n" +
//                "    airline.name AS airline_name, \n" +
//                "    COUNT(aircraft.id) AS number_of_aircraft\n" +
//                "FROM \n" +
//                "    aircraft\n" +
//                "JOIN \n" +
//                "    airline ON aircraft.airline = airline.id\n" +
//                "GROUP BY \n" +
//                "    airline.name;";
//
//        var result = DataImporter.Companion.runQuery(query, mdb.getConnection(), QueryResult.class)
//                .getAllFromColumn("number_of_aircraft", Integer.class);
//
//        assertNotNull(result);
//
//    }
//
//    @AfterEach
//    void cleanUp() throws Exception {
//        tDatabaseService.destroy();
//    }
}
