package org.mf.langchain.DTO;

import com.google.gson.GsonBuilder;

public record RelationCardinality(
        String source,
        String target,
        int min,
        int max,
        double avg

){
    public RelationCardinality(Relations relation, int min, int max, double avg) {
        this(relation.table_source, relation.table_target, min, max, avg);
    }

    @Override
    public String toString() {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
