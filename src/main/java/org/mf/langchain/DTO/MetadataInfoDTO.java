package org.mf.langchain.DTO;

import java.util.ArrayList;
import java.util.List;

public class MetadataInfoDTO {
        private String sql;
        private List<RelationCardinality> relations;

        public MetadataInfoDTO(){}
        public MetadataInfoDTO(String sql, List<RelationCardinality> relations) {
            this.sql = sql;
            this.relations = relations;
        }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<RelationCardinality> getRelations() {
        return relations;
    }

    public void setRelations(List<RelationCardinality> relations) {
        this.relations = relations;
    }
}
