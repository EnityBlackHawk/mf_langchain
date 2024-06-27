package org.mf.langchain.DTO;

import java.util.ArrayList;
import java.util.List;

public record MetadataInfoDTO(
        String sql,
        List<RelationCardinality> relations
)  {
}
