package org.mf.langchain;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mf.langchain.auto.Airline;
import org.mf.langchain.metadata.DbMetadata;
import org.mf.langchain.runtimeCompiler.MfRuntimeCompiler;
import org.mf.langchain.service.CompilerService;
import org.mf.langchain.service.LLMService;
import org.mf.langchain.service.TDatabaseService;
import org.mf.langchain.util.QueryResult;
import org.mf.langchain.util.TemplatedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LangchainApplicationTests {

    private final CompilerService _compilerService;

    @BeforeAll
    static void setUp() {
        System.out.println("Starting tests");
        LangchainApplication.INSERT_TEST_DATA = true;
    }

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
        var result = _compilerService.compileAndRun("MfRuntimeClass", "import lombok.Data;\n @Data public class MfRuntimeClass { private String name = \"Luan\"; public String hello() { return \"Hello \" + name; }}");
        assertEquals("Luan", result);
    }

//    @Test
//    void compileModelAndCopyData() throws Exception {
//
//        // Connect to the databases
//        var dbm = new DbMetadata("jdbc:postgresql://localhost:5432/airport3", "admin", "admin", null);
//        MongoClient mClient = MongoClients.create("mongodb://localhost:27017/migrationAuto");
//        MongoTemplate mTemplate = new MongoTemplate(mClient, "migrationAuto");
//
//        // Compile the model
//        Class<?> result = MfRuntimeCompiler.compile("Airline", MockLayer.MOCK_GENERATE_JAVA_CODE);
//
//        // Get and convert the data
//        var qr = DataImporter.Companion.runQuery("SELECT * FROM airline", dbm, QueryResult.class);
//        var airlines = qr.asObject(result);
//
//        // Copy the data
//        for(var airline : airlines) {
//            mTemplate.insert(airline);
//        }
//    }
    @Test
    void compileModelWithDBRefAndCopyData() throws Exception {
        // Connect to the databases
        var dbm = new DbMetadata("jdbc:postgresql://localhost:5432/airport3", "admin", "admin", null);
        MongoClient mClient = MongoClients.create("mongodb://localhost:27017/migrationAuto");
        MongoTemplate mTemplate = new MongoTemplate(mClient, "migrationAuto");


         // var cazz = CompilerUtils.CACHED_COMPILER.loadFromJava("Airline", MockLayer.MOCK_GENERATE_JAVA_CODE.get("Airline"));

         var classes = MfRuntimeCompiler.compile(MockLayer.MOCK_GENERATE_JAVA_CODE, null);

        // Get and convert the data
        var qr = DataImporter.Companion.runQuery("SELECT * FROM aircraft", dbm, QueryResult.class);
        var aircrafts = qr.asObject(classes.get("Aircraft"));

        // Copy the data
        for(var aircraft : aircrafts) {
            mTemplate.insert(aircraft);
        }
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
