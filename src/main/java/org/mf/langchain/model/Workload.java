package org.mf.langchain.model;

import jakarta.persistence.*;
import org.mf.langchain.DTO.SpecificationDTO;

@Entity
public class Workload {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "workload_seq")
        @SequenceGenerator(name = "workload_seq", sequenceName = "workload_seq", allocationSize = 1)
        private Integer id;
        private Integer regularity;
        @Column(columnDefinition = "TEXT")
        private String query;


        public Workload(Integer id, Integer regularity, String query){
            this.id = id;
            this.regularity = regularity;
            this.query = query;
        }
    public Workload(SpecificationDTO.WorkloadDTO dto){
        this(null, dto.regularity(), dto.query());
    }

    public Workload() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRegularity() {
        return regularity;
    }

    public void setRegularity(Integer regularity) {
        this.regularity = regularity;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
