package org.mf.langchain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_AIRCRAFT")
public class Aircraft {

    @Id
    private int id;
    private String type;

    @ManyToOne
    @JoinColumn(name = "airline")
    private Airline airline;

    @ManyToOne
    @JoinColumn
    private Manufacturer manufacturer;

    private String registration;
    private int max_passengers;

}
