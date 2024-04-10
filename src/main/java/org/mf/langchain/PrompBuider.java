package org.mf.langchain;

import org.mf.langchain.metadata.DbMetadata;

public record PrompBuider(DbMetadata dbMetadata, String structureOptions) {
    public static class StructureOptions {
        public static String PREFER_PERFORMANCE = "in fast queries by embedding documents";
        public static String PREFER_CONSISTENCY = "in less redundant data using DBRef";
    }

    @Override
    public String toString() {
        String s = "";
        for(var x  : dbMetadata.getTables()){
            s = s.concat(x.toString() + "\n");
        }
        return "Using this database: \n" + s +
                "Generate a structure for this database be moved to MongoDB the way that results " + structureOptions + " and the Spring Data MongoDB classes for this data base please, use Lombok";
    }
}
