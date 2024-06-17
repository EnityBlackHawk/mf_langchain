package org.mf.langchain.metadata;

import org.mf.langchain.DTO.Relations;

public record RelationsCardinality (
        String source,
        String target,
        int min,
        int max,
        double avg

){
    public RelationsCardinality(Relations relation, int min, int max, double avg) {
        this(relation.table_source, relation.table_target, min, max, avg);
    }
}
