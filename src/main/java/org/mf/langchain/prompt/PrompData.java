package org.mf.langchain.prompt;

import lombok.Data;
import org.mf.langchain.metadata.DbMetadata;
import org.springframework.data.util.Pair;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

@Data
public class PrompData implements Iterator<String> {

    private List<Query> queryList;
    private MigrationPreferences migrationPreference;
    private DbMetadata dbMetadata;
    private Framework framework;
    private int callCount = 0;
    private String sqlTables;
    private boolean allowReferences;
    private List<String> customPrompts;

    public PrompData(DbMetadata dbMetadata, MigrationPreferences migrationPreference, Boolean allowReferences, Framework framework, List<Query> queryList) {
        this.queryList = queryList;
        this.migrationPreference = migrationPreference;
        this.dbMetadata = dbMetadata;
        this.framework = framework;
    }

    public PrompData(String sqlTables, MigrationPreferences migrationPreference, Boolean allowReferences, Framework framework, List<Query> queryList, List<String> customPrompts) {
        this.queryList = queryList;
        this.migrationPreference = migrationPreference;
        this.framework = framework;
        this.sqlTables = sqlTables;
        this.dbMetadata = null;
        this.allowReferences = allowReferences;
        this.customPrompts = customPrompts;
    }

    public Query getQuery(int index) {
        return queryList.get(index);
    }

    public boolean hasQueries() {
        return !queryList.isEmpty();
    }

    public Pair<String, String> getSqlTablesAndQueries(){
        String s = "";
        if(dbMetadata != null) {
            for (var x : dbMetadata.getTables()) {
                s = s.concat(x.toString() + "\n");
            }
        }
        else {
            s = sqlTables;
        }

        String q = "";
        for(var x : queryList) {
            q = q.concat(x.query() + (x.regularity() != null ? "\n" + "- This query is used " + x.regularity() + "% of the time\n" : ""));
        }
        return Pair.of(s, q);
    }

    public String get() {

        var tq = getSqlTablesAndQueries();
        String s = tq.getFirst();
        String q = tq.getSecond();

         var result =  "Suggest a MongoDB structure for this relational database: \n" + s + "\n" +
                 (!q.isEmpty() ? "- Please consider a structure that are optimized for this queries: \n" + q : "") +
                 "\n" + "- Please consider a structure that: \n" +
                "- Use Lombok \n" +
                "- Optimized for "+ framework.getFramework() +" framework \n" +
                 (allowReferences ? "- Can use references when necessary \n" : "- Do not use references, only use embedded the documents \n") +
                "- " + migrationPreference.getDescription() + "\n";

         if(customPrompts != null)
             for(var x : customPrompts) {
                 result = result.concat("- " + x + "\n");
             }
         return result;
    }

    @Override
    public boolean hasNext() {
        return callCount < 2;
    }

    @Override
    public String next() {
        callCount++;

        var tq = getSqlTablesAndQueries();
        String s = tq.getFirst();
        String q = tq.getSecond();

        return switch (callCount) {
            case 1 -> "Suggest a MongoDB structure for this relational database: \n" + s + "\n" + "- Please consider a structure that are optimized for this queries: \n" + q + "\n" + "- Please consider a structure that " + migrationPreference.getDescription();

            case 2 -> "Generate Java classes for that MongoDB structure: \n" +
                    "- Use Lombok \n" +
                    "- Optimized for "+ framework.getFramework() +" framework \n" +
                    "- " + ((migrationPreference == MigrationPreferences.PREFER_PERFORMANCE) ? "Some documents are embedded" : "Use @DBRef annotation");
            default -> null;
        };
    }
}
