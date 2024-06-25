package org.mf.langchain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mf.langchain.DTO.RelationsCardinalityDTO;

@Entity
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }
}
