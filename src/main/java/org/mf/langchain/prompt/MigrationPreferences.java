package org.mf.langchain.prompt;

public enum MigrationPreferences {
    PREFER_PERFORMANCE("has embedded documents, not references"),
    PREFER_CONSISTENCY("has less redundant data");

    private final String description;

    MigrationPreferences(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
