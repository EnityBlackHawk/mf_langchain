package org.mf.langchain.connections;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MongoTemplateRegistry {

    private final DynamicMongoProperties _properties;
    private final Map<String, MongoTemplate> _templates = new HashMap<>();

    public MongoTemplateRegistry(DynamicMongoProperties properties) {
        _properties = properties;
    }

    private void initializeConnections()
    {
        for (var connection : _properties.getConnections()) {
            var template =  createMongoTemplate(connection);
            _templates.put(connection.getId(), template);
        }
    }

    public MongoTemplate getTemplate(String id) {
        return _templates.get(id);
    }

    public void addMongoTemplate(MongoConnection connection) {
        var template = createMongoTemplate(connection);
        _templates.put(connection.getId(), template);
    }

    public void removeMongoTemplate(String id) {
        _templates.remove(id);
    }

    private MongoTemplate createMongoTemplate(MongoConnection connection) {
        String uri = connection.getUsername() != null ? String.format("mongodb://%s:%s@%s:%d/%s",
                connection.getUsername(),
                connection.getPassword(),
                connection.getHost(),
                connection.getPort(),
                connection.getDatabase())
                :
                String.format("mongodb://%s:%d/%s",
                        connection.getHost(),
                        connection.getPort(),
                        connection.getDatabase());
        MongoClient mongoClient = MongoClients.create(uri);
        return new MongoTemplate(mongoClient, connection.getDatabase());
    }
}
