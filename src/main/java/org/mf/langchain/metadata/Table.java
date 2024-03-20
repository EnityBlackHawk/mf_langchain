package org.mf.langchain.metadata;

import java.util.List;

public record Table(String name, List<Column> columns) {

    @Override
    public String toString() {
        String fkr = "";
        String s = name + " ( ";
        for (Column e : columns) {
            s = s.concat(e.toString() + ", ");
            if(e.isFk()){
                assert e.fkInfo() != null;
                fkr = fkr.concat(e.fkInfo().toString() + ", ");
            }
        }
        s = s.concat(") " + fkr);
        return s;
    }
}
