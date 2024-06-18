package org.mf.langchain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mf.langchain.DTO.RelationsCardinalityDTO;

@Entity
@Data
@AllArgsConstructor
public class RelationsCardinality {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "relations_cardinality_seq")
    @SequenceGenerator(name = "relations_cardinality_seq", sequenceName = "relations_cardinality_seq", allocationSize = 1)
    private Integer id;
    private String source;
    private String target;
    private int min;
    private int max;
    private double avg;


    public RelationsCardinality(RelationsCardinalityDTO dto) {
        this.source = dto.source();
        this.target = dto.target();
        this.min = dto.min();
        this.max = dto.max();
        this.avg = dto.avg();
    }

    public RelationsCardinality() {

    }
}
