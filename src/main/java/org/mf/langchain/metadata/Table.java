package org.mf.langchain.metadata;

import java.util.List;

public record Table(String name, List<Column> columns) {
}
