package org.mf.langchain.DTO;

public class Credentials {
        private String connectionString;
        private String username;
        private String password;

        public Credentials(){}
        public Credentials(String connectionString, String username, String password) {
            this.connectionString = connectionString;
            this.username = username;
            this.password = password;
        }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
