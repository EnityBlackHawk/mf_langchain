package org.mf.langchain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class BasicRuns {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "basic_runs_seq")
    @SequenceGenerator(name = "basic_runs_seq", sequenceName = "basic_runs_seq", allocationSize = 1)
    private Integer id;
    @Column(columnDefinition = "TEXT", length = 1000)
    private String prompt;
    @Column(columnDefinition = "TEXT", length = 1000)
    private String result;
}
