package org.mf.langchain;

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

        var connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/air", "postgres", "admin");
        if(connection.isClosed())
            throw new RuntimeException("ERROR MANOOO");
        var metaData = connection.getMetaData();

        String name = metaData.getDatabaseProductName();

        System.out.println(name);
        ResultSet tbs = metaData.getTables(null, null, null, new String[] {"TABLE"});

        while(tbs.next()) {
            String tb_name = tbs.getString("TABLE_NAME");
            System.out.print(tb_name + " ");
            ResultSet cls = metaData.getColumns(null, null, tb_name, null);
            while (cls.next())
            {
                String columnName = cls.getString("COLUMN_NAME");
                String datatype = cls.getString("DATA_TYPE");
                System.out.print("(" + columnName + " - " + SqlDataType.getByValue(Integer.parseInt(datatype)) + " ) ");
            }
            System.out.print("\n");
        }

    }
}
