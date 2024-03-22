package com.grupo1.domain.aggregates.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.*;

import java.sql.Timestamp;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TriajeDTO {

    private  Long idTriaje;
    private Timestamp fechaTriaje;
    private Double tempCorporal;
    private String presionArterial;
    private Integer satOxigeno;
    private String sintoma;
    private String observaciones;

}
