package org.mf.langchain;

import org.mf.langchain.metadata.DbMetadata;
import org.mf.langchain.repositories.AirlineRepository;
import org.mf.langchain.repositories.AirportRepository;
import org.mf.langchain.service.GeminiService;
import org.mf.langchain.util.SqlDataType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class LangchainApplication {


    public static void main(String[] args) throws IOException, SQLException {
        var context = SpringApplication.run(LangchainApplication.class, args);

//        StreamingChatLanguageModel model = LocalAiStreamingChatModel.builder()
//                .modelName("phi-2")
//                .baseUrl("http://localhost:8080")
//                .timeout(Duration.ofDays(1))
//                .temperature(0.8)
//                .build();
        // AIzaSyBiyiDDkyHuu3d98AZmHX9nTxmheKKBfWk

//        StreamingChatLanguageModel model = VertexAiGeminiStreamingChatModel.builder()
//                .modelName("gemini-pro")
//                .project("gen-lang-client-0095677353")
//                .location("pt-BR")
//                .build();
//
//        Assistant assistant = AiServices.create(Assistant.class, model);
//
//        var ts = assistant.chat("Considering a relational bank:\\n Clients(id, name)\\n Invoice(id, revenue, client_id) client_id references Clients\\n Generate a Java class to generate MongoDB documents following the following specifications:\\nClients -> Invoice");
//        ts.onNext(System.out::print).onError(Throwable::printStackTrace).start();


        var airportRepo = context.getBean(AirportRepository.class);
        var airlinesRepo = context.getBean(AirlineRepository.class);

        DataImporter.Companion.importAirports("C:\\Users\\Luan\\Documents\\mf_langchain\\airports.json", airportRepo);
        DataImporter.Companion.importAirlines("C:\\Users\\Luan\\Documents\\mf_langchain\\airlines.json", airlinesRepo);


        var dbc = new DbMetadata("jdbc:h2:mem:testdb", "sa", "password", "TB_%");
        String s = "";
        for(var x  : dbc.getTables()){
            s = s.concat(x.toString() + "\n");
        }
        System.out.println(s);
        GeminiService gs = context.getBean(GeminiService.class);
        System.out.print(
                gs.getCompletion("Using this database: " + s + " Eu quero migrar meu banco relacional para o não relaional. Qual o melhor esquema de banco de dados NoSQL orientado a documentos (MongoDB) eu devo utilizar ? Gere um json_schema da organização dos dados")
        );
    }
}
