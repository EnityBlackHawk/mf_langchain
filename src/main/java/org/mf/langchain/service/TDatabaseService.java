package org.mf.langchain.service;

import org.mf.langchain.DTO.CreateDatabaseDTO;
import org.mf.langchain.DTO.Credentials;
import org.mf.langchain.DTO.ResponseCreateDatabaseDTO;
import org.mf.langchain.DataImporter;
import org.mf.langchain.metadata.DbMetadata;
import org.mf.langchain.util.DatabaseInserter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

@Service
public class TDatabaseService {

    private String baseConnectionString;
    private String username;
    private String password;
    private Connection mainConnection;
    private HashMap<String, Connection> connections = new HashMap<>();

    public TDatabaseService(
            @Value("${spring.datasource.url}")
            String connectionString,
            @Value("${spring.datasource.username}")
            String username,
            @Value("${spring.datasource.password}")
            String password)
    {
        String f = "";
        for(int i = connectionString.length() - 1; i >= 0; i--) {
            if(connectionString.charAt(i) == '/') {
                f = connectionString.substring(0, i);
                break;
            }
        }

        this.baseConnectionString = f;
        this.username = username;
        this.password = password;

        try {
            this.mainConnection = DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Credentials getCredentials(String dbName) {
        return new Credentials(baseConnectionString + "/" + dbName, username, password);
    }

    public Credentials createDatabase(String databaseName) throws SQLException {
        boolean isNew = true;
        var result = DataImporter.Companion.createDatabase(mainConnection, databaseName);
        if(!result.equals("OK"))
        {
            System.out.println("Error creating database: " + result + "\n Connecting to an existing database");
            isNew = false;
        }
        var conn = DriverManager.getConnection(baseConnectionString + "/" + databaseName, username, password);
        connections.put(databaseName, conn);
        return new Credentials(baseConnectionString + "/" + databaseName, username, password, isNew ? Credentials.CreationMethod.CREATE_DATABASE : Credentials.CreationMethod.USE_EXISTING);
    }

    public ResponseCreateDatabaseDTO createDatabaseAndExecuteSQL(String databaseName, String sql) {
        DataImporter.Companion.createDatabase(mainConnection, databaseName);
        try {
            var conn = DriverManager.getConnection(baseConnectionString + "/" + databaseName, username, password);
            var exRes = sql == null ? "Not executed" : DataImporter.Companion.runSQL(sql, conn);
            connections.put(databaseName, conn);
            return new ResponseCreateDatabaseDTO(baseConnectionString + "/" + databaseName, exRes, databaseName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String executeSQL(String databaseName, String sql) {
        var conn = connections.getOrDefault(databaseName, null);
        if(conn == null) throw new RuntimeException("Database not found");
        return DataImporter.Companion.runSQL(sql, conn);
    }

    public String getCreateSQL(String databaseName) {
        var conn = connections.getOrDefault(databaseName, null);
        if(conn == null) throw new RuntimeException("Database not found");
        DbMetadata metadata = null;
        try {
            metadata = new DbMetadata(conn, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return metadata.toString();
    }

    @PostConstruct
    public void createTestDatabase() throws SQLException {
        var cred = createDatabase("airport3");
        var mdb = new DbMetadata(cred, null);
        if(cred.getCreationMethod() == Credentials.CreationMethod.CREATE_DATABASE)
        {
            DataImporter.Companion.runSQLFromFile("src/main/resources/data.sql", mdb.getConnection());
            //DataImporter.Companion.runSQLFromFile("inserts.sql", mdb.getConnection());
            DatabaseInserter inserter = new DatabaseInserter(cred);
            inserter.insertData();
        }
    }

    @PreDestroy
    public void destroy() throws Exception {
        for(var conn : connections.keySet()) {
            DataImporter.Companion.runSQL("DROP DATABASE " + conn + " WITH (FORCE);", mainConnection);
        }
        mainConnection.close();
    }
}
