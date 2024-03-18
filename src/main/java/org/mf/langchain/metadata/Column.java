package org.mf.langchain.metadata;

import org.mf.langchain.util.SqlDataType;

public record Column(String name, SqlDataType dataType) {

    @Override
    public String toString() {
        return name + " : " + dataType.name();
    }
}
