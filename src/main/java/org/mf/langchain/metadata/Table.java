package org.mf.langchain.metadata;

import java.util.List;

public record Table(String name, List<Column> columns) {

    @Override
    public String toString() {
        final String[] s = {name + " ( "};
        columns.forEach((e) -> {
            s[0] = s[0].concat(e.toString() + ", ");
        });
        s[0] = s[0].concat(")");
        return s[0];
    }
}
