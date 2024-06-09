package org.mf.langchain.prompt;

import org.jetbrains.annotations.Nullable;
import org.mf.langchain.metadata.DbMetadata;
import org.springframework.data.util.Pair;

import java.util.List;

public class PromptData2 extends PromptData {

    private final Boolean useMarkdown;
    private final String cardinalityTable;

    public PromptData2(DbMetadata dbMetadata, MigrationPreferences migrationPreference, Boolean allowReferences, Framework framework, @Nullable String cardinalityTable, Boolean useMarkdown, List<Query> queryList, List<String> remarks) {
        super(dbMetadata, migrationPreference, allowReferences, framework, queryList, remarks);
        this.useMarkdown = useMarkdown;
        this.cardinalityTable = cardinalityTable;
    }

    public PromptData2(String sqlTables, MigrationPreferences migrationPreference, Boolean allowReferences, Framework framework, @Nullable String cardinalityTable, Boolean useMarkdown, List<Query> queryList, List<String> customPrompts) {
        super(sqlTables, migrationPreference, allowReferences, framework, queryList, customPrompts);
        this.useMarkdown = useMarkdown;
        this.cardinalityTable = cardinalityTable;
    }

    @Override
    public String get() {

        StringBuilder sb = new StringBuilder();
        var infos = this.getSqlTablesAndQueries();

        sb.append("# Suggest a MongoDB structure for this relational database: \n")
                .append("```sql\n")
                .append(infos.getFirst())
                .append("\n```\n");
        if(!queryList.isEmpty())
        {
            sb.append("## Take into consideration this most used queries: \n");
            sb.append(infos.getSecond());
        }
        if(cardinalityTable != null)
        {
            sb.append("## Cardinality Table: \n");
            sb.append(cardinalityTable);
        }

        sb.append("## Remarks: \n");
        for (var x : customPrompts) {
            sb.append("- ").append(x).append("\n");
        }
        var result = sb.toString();
        return useMarkdown ? result : removeMarkdown(result);
    }

    private String removeMarkdown(String s) {
        return s.replaceAll("(```sql|```|'|-|##|#)", "");
    }

    @Override
    public Pair<String, String> getSqlTablesAndQueries() {
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
            q = q.concat("- " + (x.regularity() != null ? "\n" + "- **Used " + x.regularity() + "% of the time**: " : "") + "`" + x.query() + "`\n");
        }
        return Pair.of(s, q);
    }

    @Override
    @Deprecated
    public boolean hasNext() {
        return super.hasNext();
    }

    @Override
    @Deprecated
    public String next() {
        return super.next();
    }
}
