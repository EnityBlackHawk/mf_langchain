package org.mf.langchain.DTO;

public record Credentials(
        String connectionString,
        String username,
        String password
) {
}
