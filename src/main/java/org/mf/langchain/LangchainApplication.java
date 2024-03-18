package org.mf.langchain;

import org.mf.langchain.metadata.DbMetadata;
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

        var dbc = new DbMetadata("jdbc:postgresql://localhost:5432/air", "postgres", "admin");
        System.out.print(dbc.getTables());

    }
}
