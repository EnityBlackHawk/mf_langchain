package org.mf.langchain.connections;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
// @ConfigurationProperties(prefix = "spring.data.mongodb")
@ConfigurationProperties(prefix = "mongodb")
public class DynamicMongoProperties {

    private ArrayList<MongoConnection> _connections;

    public List<MongoConnection> getConnections() {
        return _connections;
    }

    public void setConnections(ArrayList<MongoConnection> connections) {
        _connections = connections;
    }

    public MongoConnection getConnection(String id) {
        return _connections.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    public void addConnection(MongoConnection connection) {
        _connections.add(connection);
    }

    public void removeConnection(String id) {
        _connections.removeIf(c -> c.getId().equals(id));
    }

}
