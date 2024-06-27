package org.mf.langchain.DTO;

public enum RelationMigrationPreference {

    REFERENCE ("reference"),
    EMBEDDED ("embedded");

    private final String preference;

    RelationMigrationPreference(String preference) {
        this.preference = preference;
    }

    public String getPreference() {
        return preference;
    }
}
