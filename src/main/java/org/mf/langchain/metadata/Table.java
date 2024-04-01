package org.mf.langchain.metadata;

import java.util.List;

public record Table(String name, List<Column> columns) {

    @Override
    public String toString() {
        String s = "CREATE TABLE " + name + " ( \n";
        int count = 0;
        for (Column e : columns) {
            s = s.concat("\t" + e.toString() + (count < columns.size() - 1 ? ",\n" : "\n"));
            count++;
        }
        s = s.concat(");");
        return s;
    }
}
