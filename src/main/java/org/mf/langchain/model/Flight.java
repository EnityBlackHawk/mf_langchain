package org.mf.langchain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_FLIGHT")
public class Flight {

    @Id
    private String number;
    @ManyToOne
    @JoinColumn(name = "airport_from")
    private Airport airport_from;
    @ManyToOne
    @JoinColumn(name = "airport_to")
    private Airport airport_to;

    private Date departure_time_scheduled;
    private Date departure_time_actual;
    private Date arrival_time_scheduled;
    private Date arrival_time_actual;
    private int gate;
    @ManyToOne
    @JoinColumn(name = "connects_to")
    private Flight connects_to;

    @ManyToOne
    @JoinColumn
    private Aircraft aircraft;

}
