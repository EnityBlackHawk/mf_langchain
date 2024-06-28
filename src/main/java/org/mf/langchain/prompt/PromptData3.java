package org.mf.langchain.prompt;

import org.jetbrains.annotations.Nullable;
import org.mf.langchain.metadata.DbMetadata;

import java.util.List;

public class PromptData3 extends PromptData2{
    public PromptData3(DbMetadata dbMetadata, MigrationPreferences migrationPreference, Boolean allowReferences, Framework framework, @Nullable String cardinalityTable, Boolean useMarkdown, List<Query> queryList, List<String> remarks) {
        super(dbMetadata, migrationPreference, allowReferences, framework, cardinalityTable, useMarkdown, queryList, remarks);
    }

    public PromptData3(String sqlTables, MigrationPreferences migrationPreference, Boolean allowReferences, Framework framework, @Nullable String cardinalityTable, Boolean useMarkdown, List<Query> queryList, List<String> customPrompts) {
        super(sqlTables, migrationPreference, allowReferences, framework, cardinalityTable, useMarkdown, queryList, customPrompts);
    }

    @Override
    public String get() {
        return getFirst() + "\n" + getSecond();
    }

    public String getFirst() {
        StringBuilder sb = new StringBuilder();
        var infos = getSqlTablesAndQueries();
        sb.append("You are an expert in database modeling. Your task is to help migrate a relational database to a MongoDB database. Follow the instructions and details provided below to generate the MongoDB model. \n")
                .append("### Task Overview\n")
                .append("We have a relational database that needs to be migrated to MongoDB. The goal is to create an optimized MongoDB schema based on the usage patterns of the data. \n");

        sb.append("### Relational Database Schema").append("\n");
        sb.append("Here is the schema of our current relational database:").append("\n");
        sb.append("```sql").append("\n");
        sb.append(infos.getFirst()).append("\n");
        sb.append("```").append("\n");

        sb.append("### MongoDB Model Considerations").append("\n");

        if(queryList != null) {
            sb.append("- Optimize for the following frequently used queries:").append("\n");
            for(var query : queryList) {
                sb.append("\t- ").append("**Used ").append(query.regularity()).append("% of the time:** ").append(query.query()).append("\n");
            }
        }
        sb.append( allowReferences ? "- Use references for less frequently accessed data \n" : "");
        sb.append("- ").append( migrationPreference.getDescription()).append("\n");

        if(userDefinedPrompts != null) {
            for(var x : userDefinedPrompts)
                sb.append("- ").append(x).append("\n");
        }

        sb.append("### Output format").append("\n");
        sb.append("- MongoDB models in JSON format").append("\n");

        sb.append("Please generate the MongoDB model");

        return sb.toString();
    }

    public String getSecond() {

        String sb = "### Java Code Requirements" + "\n" +
                "- Generate Java classes for the MongoDB model" + "\n" +
                "- Use Lombok annotations for data classes" + "\n" +
                "- Use " + framework.getFramework() + " framework for the Java code" + "\n" +
                "### Output format" + "\n" +
                "- Java code in separate classes for each entity" + "\n" +
                "Please generate the Java code for the MongoDB model based on provided details";
        return sb;
    }


    @Override
    public String next() {
        addCallCount();
        return switch (getCallCount()) {
            case 1 -> getFirst();
            case 2 -> getSecond();
            default -> "";
        };
    }
}
