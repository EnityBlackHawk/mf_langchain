package org.mf.langchain.prompt;

import lombok.Data;
import org.mf.langchain.metadata.DbMetadata;

import java.util.List;

@Data
public class PrompData {

    private List<Query> queryList;
    private MigrationPreferences migrationPreference;
    private DbMetadata dbMetadata;

    public PrompData(DbMetadata dbMetadata, MigrationPreferences migrationPreference, List<Query> queryList) {
        this.queryList = queryList;
        this.migrationPreference = migrationPreference;
        this.dbMetadata = dbMetadata;
    }

    public Query getQuery(int index) {
        return queryList.get(index);
    }

    public boolean hasQueries() {
        return !queryList.isEmpty();
    }
}
